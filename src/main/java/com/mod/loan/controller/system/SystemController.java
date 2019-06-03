package com.mod.loan.controller.system;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.mod.loan.common.enums.ResponseEnum;
import com.mod.loan.common.model.RequestThread;
import com.mod.loan.common.model.ResultMessage;
import com.mod.loan.config.Constant;
import com.mod.loan.config.rabbitmq.RabbitConst;
import com.mod.loan.config.redis.RedisConst;
import com.mod.loan.config.redis.RedisMapper;
import com.mod.loan.mapper.CompanyMapper;
import com.mod.loan.mapper.DepartmentMapper;
import com.mod.loan.mapper.LoginRecordMapper;
import com.mod.loan.model.Company;
import com.mod.loan.model.Department;
import com.mod.loan.model.LoginRecord;
import com.mod.loan.model.Manager;
import com.mod.loan.model.Resource;
import com.mod.loan.model.sms.SmsMessage;
import com.mod.loan.service.ManagerService;
import com.mod.loan.service.ResourceService;
import com.mod.loan.service.RoleService;
import com.mod.loan.util.CookieUtils;
import com.mod.loan.util.HttpUtils;
import com.mod.loan.util.JwtUtil;
import com.mod.loan.util.MD5;
import com.mod.loan.util.RandomUtils;
import com.mod.loan.util.StringUtil;

/**
 *
 * @author wgy
 *
 */
@RestController
@RequestMapping(value = "system")
public class SystemController {

	private static final Logger logger = LoggerFactory.getLogger(SystemController.class);

	@Autowired
	private ManagerService managerService;
	@Autowired
	private CompanyMapper companyMapper;
	@Autowired
	private DepartmentMapper departmentMapper;
	@Autowired
	private ResourceService resourceService;
	@Autowired
	private RedisMapper redisMapper;
	@Autowired
	private LoginRecordMapper loginRecordMapper;
	@Autowired
	private RoleService roleService;
	@Autowired
	private RabbitTemplate rabbitTemplate;

	/**
	 * 进入tab标签
	 *
	 * @return
	 */
	@RequestMapping(value = "tab")
	public ModelAndView tab(ModelAndView view) {
		view.setViewName("tab");
		return view;
	}

	/**
	 * 空白页面
	 *
	 * @param view
	 * @return
	 */
	@RequestMapping(value = "blank")
	public ModelAndView blank(ModelAndView view) {
		view.setViewName("common/blank");
		return view;
	}

	// 首页跳转
	@RequestMapping(value = "index")
	public ModelAndView index(HttpServletRequest request, ModelAndView view) {
		List<Resource> resourceList = resourceService.findByManagerId(RequestThread.get().getUid());
		view.addObject("resourceList", resourceList);
		view.setViewName("index");
		return view;
	}

	// 后台首页跳转
	@RequestMapping(value = "main")
	public ModelAndView main(ModelAndView view) {
		view.setViewName("main");
		return view;
	}

	// 后台首页请求
	@RequestMapping(value = "main_ajax", method = { RequestMethod.POST })
	public ResultMessage main_ajax() {
		Map<String, String> data = null;
		return new ResultMessage(ResponseEnum.M2000, data);
	}

	// 登录页面
	@RequestMapping(value = "login")
	public ModelAndView login(ModelAndView view) {
		view.setViewName("login");
		return view;
	}

	@RequestMapping(value = "login_ajax", method = { RequestMethod.POST })
	public ResultMessage login_ajax(HttpServletRequest request, HttpServletResponse response, String loginname, String password, String code) {
		Manager manager = new Manager();
		manager.setLoginName(loginname.trim());
		manager = managerService.selectOne(manager);
		if (manager == null) {
			return new ResultMessage(ResponseEnum.M4000.getCode(), "用户名或密码错误");
		}
		// 非超级管理员账号判断角色状态
		if (manager.getAccountType() == 0 && roleService.selectByPrimaryKey(manager.getUserRoleId()).getRoleStatus() != 0) {
			return new ResultMessage(ResponseEnum.M4000.getCode(), "您的帐号已被冻结请联系管理员");
		}
		if (manager.getAccountStatus() == 1) {
			return new ResultMessage(ResponseEnum.M4000.getCode(), "您的帐号已被冻结请联系管理员");
		}

		String ip = HttpUtils.getIpAddr(request, ".");
		String ua = request.getHeader("User-Agent");
		String host = request.getHeader("Host");
		if (!manager.getLoginPassword().equals(MD5.toMD5(password))) {
			long increment = redisMapper.increment(RedisConst.PASSWORD_ERROR_FLAG + loginname, 1L, 3600L);
			if (increment > 6) {
				logger.error("密码错误次数过多，冻结账号。ip={},login_name={}", ip, manager.getLoginName());
				return lockManager(manager, ip);
			}
			return new ResultMessage(ResponseEnum.M4000.getCode(), "用户名或密码错误");
		}

		if (manager.getUserSecurity() == 1) {
            if (!StringUtil.isMobiPhoneNum(manager.getUserPhone())) {
                return new ResultMessage(ResponseEnum.M4000.getCode(), "请正确填写当前账号的手机号码");
            }
            if (StringUtils.isBlank(code)) {
                return new ResultMessage(ResponseEnum.M5001, StringUtil.phoneReplaceWithStar(manager.getUserPhone()));
            }
            if (code.length() != 6) {
                return new ResultMessage(ResponseEnum.M4005);
            }
            // 安全账户ip异常需要短信验证码
            if (!managerService.verifyIp(manager, ip)) {
                long increment = redisMapper.increment(RedisConst.USER_SECURITY_CODE + Constant.MOD_PROJECT_NAME + ":" + loginname, 1L, 3600L);
                if (increment > 20) {
                    logger.error("一小时内验证码输入次数过多，冻结账号。ip={},login_name={}", ip, manager.getLoginName());
                    return lockManager(manager, ip);
                }
            }
            if (!code.equals(redisMapper.get(RedisConst.USER_SECURITY_CODE + manager.getUserPhone()))) {
                return new ResultMessage(ResponseEnum.M4005);
            }
        }
		manager.setLastLoginTime(new Date());
		manager.setLastLoginIp(ip);
		managerService.updateByPrimaryKeySelective(manager);

		// 生成token
		String key = MD5.toMD5(String.format("%s:%s:%s", host, ip, ua));
		Map<String, String> map = new HashMap<>();
		map.put("uid", manager.getId().toString());
		map.put("key", key);
		String token = JwtUtil.generToken(map);
		redisMapper.set(RedisConst.USER_TOKEN + manager.getId(), token, 7200);
		CookieUtils.setCookie(response, Constant.cookie_token, token);

		// 插入登录记录
		LoginRecord loginRecord = new LoginRecord();
		loginRecord.setManagerId(manager.getId());
		loginRecord.setMerchant(Constant.MOD_PROJECT_NAME);
		loginRecord.setLoginName(loginname);
		loginRecord.setUserName(manager.getUserName());
		loginRecord.setLoginIp(ip);
		loginRecord.setUserUa(ua);
		loginRecord.setUserHost(host);
		loginRecord.setCreateTime(new Date());
		loginRecordMapper.insert(loginRecord);

		// 登陆完成后删除验证码缓存
		redisMapper.remove(RedisConst.USER_SECURITY_CODE + manager.getUserPhone());
		return new ResultMessage(ResponseEnum.M2000);
	}

	@RequestMapping(value = "login_phone_code_ajax", method = { RequestMethod.POST })
	public ResultMessage login_phone_code_ajax(HttpServletRequest request, String loginname, String password) {
		Manager manager = new Manager();
		manager.setLoginName(loginname.trim());
		manager = managerService.selectOne(manager);
		if (manager == null) {
			return new ResultMessage(ResponseEnum.M4000.getCode(), "用户名或密码错误");
		}
		// 非超级管理员账号判断角色状态
		if (manager.getAccountType() == 0 && roleService.selectByPrimaryKey(manager.getUserRoleId()).getRoleStatus() != 0) {
			return new ResultMessage(ResponseEnum.M4000.getCode(), "您的帐号已被冻结请联系管理员");
		}
		if (manager.getAccountStatus() == 1) {
			return new ResultMessage(ResponseEnum.M4000.getCode(), "您的帐号已被冻结请联系管理员");
		}
		String ip = HttpUtils.getIpAddr(request, ".");
		if (!manager.getLoginPassword().equals(MD5.toMD5(password))) {
			long increment = redisMapper.increment(RedisConst.PASSWORD_ERROR_FLAG + ":" + loginname, 1L, 3600L);
			if (increment > 6) {
				logger.error("密码错误次数过多，冻结账号。ip={},login_name={}", ip, manager.getLoginName());
				return lockManager(manager, ip);
			}
			return new ResultMessage(ResponseEnum.M4000.getCode(), "用户名或密码错误");
		}

		if (!StringUtil.isMobiPhoneNum(manager.getUserPhone())) {
			return new ResultMessage(ResponseEnum.M4000.getCode(), "请正确填写当前账号的手机号码");
		}

		// 发送验证码，5分钟内有效
		String randomNum = RandomUtils.generateRandomNum(6);
		logger.info("ip={},login_name={},登陆验证未通过,向手机={}发送短信验证码={}", ip, manager.getLoginName(), manager.getUserPhone(), randomNum);
		redisMapper.set(RedisConst.USER_SECURITY_CODE + manager.getUserPhone(), randomNum, 300);
		rabbitTemplate.convertAndSend(RabbitConst.QUEUE_SMS, new SmsMessage("mx", "1001", manager.getUserPhone(), randomNum + "|5分钟"));
		return new ResultMessage(ResponseEnum.M2000);
	}

	// 退出
	@RequestMapping(value = "exit")
	public ModelAndView exit(HttpServletRequest request, HttpServletResponse response, ModelAndView view) {
		view.setViewName("redirect:/system/index");
		redisMapper.remove(RedisConst.USER_TOKEN + RequestThread.get().getUid());
		return view;
	}

	@RequestMapping(value = "company_list_ajax", method = { RequestMethod.POST })
	public ResultMessage company_list_ajax() {
		List<Company> companyList = companyMapper.selectAll();
		return new ResultMessage(ResponseEnum.M2000, companyList);
	}

	@RequestMapping(value = "department_list_ajax", method = { RequestMethod.POST })
	public ResultMessage department_list_ajax() {
		List<Department> departmentList = departmentMapper.selectAll();
		return new ResultMessage(ResponseEnum.M2000, departmentList);
	}

	private ResultMessage lockManager(Manager manager, String ip) {
		manager.setAccountStatus(1);
		managerService.updateByPrimaryKeySelective(manager);
		return new ResultMessage(ResponseEnum.M4000.getCode(), "错误次数过多，账号已冻结，请联系管理员");
	}
}
