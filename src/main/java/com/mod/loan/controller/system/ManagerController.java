package com.mod.loan.controller.system;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.mod.loan.common.enums.ResponseEnum;
import com.mod.loan.common.model.Page;
import com.mod.loan.common.model.RequestThread;
import com.mod.loan.common.model.ResultMessage;
import com.mod.loan.config.redis.RedisConst;
import com.mod.loan.config.redis.RedisMapper;
import com.mod.loan.mapper.RoleMapper;
import com.mod.loan.model.Manager;
import com.mod.loan.model.Role;
import com.mod.loan.service.ManagerService;
import com.mod.loan.util.HttpUtils;
import com.mod.loan.util.MD5;
import com.mod.loan.util.RandomUtils;
import com.mod.loan.util.StringUtil;

@RestController
@RequestMapping(value = "system")
public class ManagerController {
	public static final Logger logger = LoggerFactory.getLogger(ManagerController.class);

	@Autowired
	private ManagerService managerService;
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private RedisMapper redisMapper;

	@RequestMapping(value = "manager_list")
	public ModelAndView manager_list(ModelAndView view) {
		view.setViewName("system/manager_list");
		return view;
	}

	@RequestMapping(value = "manager_list_ajax", method = { RequestMethod.POST })
	public ResultMessage manager_list_ajax(Manager manager, Page page) {
		return new ResultMessage(ResponseEnum.M2000, managerService.findManagerList(manager, page), page);
	}

	@RequestMapping(value = "manager_add")
	public ModelAndView manager_add(ModelAndView view) {
		view.setViewName("system/manager_add");
		return view;
	}

	@RequestMapping(value = "manager_add_ajax", method = { RequestMethod.POST })
	public ResultMessage manager_add_ajax(HttpServletRequest request, Manager manager) {
		if (!StringUtil.verifyPassword(manager.getLoginPassword())) {
			return new ResultMessage(ResponseEnum.M4000.getCode(), "密码不符合规范。");
		}
		Manager entity = new Manager();
		entity.setLoginName(manager.getLoginName());
		List<Manager> managerList = managerService.select(entity);
		if (managerList.size() > 0) {
			return new ResultMessage(ResponseEnum.M4000.getCode(), "已存在的用户名");
		}

		manager.setLoginPassword(MD5.toMD5(manager.getLoginPassword()));
		manager.setAccountStatus(0);
		manager.setLastLoginTime(new Date());
		try {
			loggerManagerOperation(request, managerService.selectByPrimaryKey(RequestThread.get().getUid()), "新增了loginname=" + manager.getLoginName() + "账户");
			managerService.insertSelective(manager);
			return new ResultMessage(ResponseEnum.M2000);
		} catch (Exception e) {
			logger.error("账户新增失败。error_msg={}", e.getMessage());
		}
		return new ResultMessage(ResponseEnum.M4000);
	}

	@RequestMapping(value = "manager_update")
	public ModelAndView manager_update(ModelAndView view, Long id) {
		view.addObject("id", id);
		view.setViewName("system/manager_edit");
		return view;
	}

	@RequestMapping(value = "manager_update_ajax", method = { RequestMethod.POST })
	public ResultMessage manager_update_ajax(HttpServletRequest request, Manager manager) {
		try {
			loggerManagerOperation(request, managerService.selectByPrimaryKey(RequestThread.get().getUid()), "修改了id=" + manager.getId() + "账户的信息");
			managerService.updateByPrimaryKeySelective(manager);
			return new ResultMessage(ResponseEnum.M2000);
		} catch (Exception e) {
			logger.error("账户修改失败。error_msg={}", e.getMessage());
		}
		return new ResultMessage(ResponseEnum.M4000);
	}

	@RequestMapping(value = "manager_detail")
	public ModelAndView manager_detail(ModelAndView view) {
		view.setViewName("system/manager_detail");
		return view;
	}

	@RequestMapping(value = "manager_detail_ajax", method = { RequestMethod.POST })
	public ResultMessage manager_detail_ajax(Long id) {
		Map<String, Object> data = Maps.newHashMap();
		if (id == null) {
			id = RequestThread.get().getUid();
		}
		Manager manager = managerService.selectById(id);
		data.put("manager", manager);
		if (manager.getUserRoleId() != null) {
            Role role = roleMapper.selectByPrimaryKey(manager.getUserRoleId());
            data.put("roleName", role.getRoleName());
        }
		return new ResultMessage(ResponseEnum.M2000, data);
	}

	// 修改密码页面
	@RequestMapping(value = "manager_update_pwd")
	public ModelAndView manager_update_pwd(ModelAndView view) {
		view.setViewName("system/manager_update_pwd");
		return view;
	}

	@RequestMapping(value = "manager_update_pwd_ajax", method = { RequestMethod.POST })
	public ResultMessage manager_update_pwd_ajax(HttpServletRequest request, String old_pwd, String new_pwd) {
		if (StringUtils.isEmpty(old_pwd)) {
			return new ResultMessage(ResponseEnum.M4001);
		}
		if (StringUtils.isEmpty(new_pwd)) {
			return new ResultMessage(ResponseEnum.M4002);
		}
		if (!StringUtil.verifyPassword(new_pwd)) {
			return new ResultMessage(ResponseEnum.M4000.getCode(), "密码不符合规范。");
		}
		Manager manager = managerService.selectByPrimaryKey(RequestThread.get().getUid());
		if (!manager.getLoginPassword().equals(MD5.toMD5(old_pwd))) {
			return new ResultMessage(ResponseEnum.M4003);
		}
		try {
			loggerManagerOperation(request, managerService.selectByPrimaryKey(RequestThread.get().getUid()), "修改了当前账户的密码");
			manager.setLoginPassword(MD5.toMD5(new_pwd));
			managerService.updateByPrimaryKeySelective(manager);
			redisMapper.remove(RedisConst.USER_TOKEN + RequestThread.get().getUid());
			return new ResultMessage(ResponseEnum.M2000);
		} catch (Exception e) {
			logger.error("账户修改密码失败,error_msg={}", e.getMessage());
		}
		return new ResultMessage(ResponseEnum.M4000);
	}

	// 账户重置密码
	@RequestMapping(value = "manager_reset_ajax", method = { RequestMethod.POST })
	public ResultMessage manager_reset_ajax(HttpServletRequest request, Long id) {
		Manager manager = managerService.selectByPrimaryKey(id);
		String password = RandomUtils.generatePassword(10).toString();
		manager.setLoginPassword(MD5.toMD5(password));
		try {
			loggerManagerOperation(request, managerService.selectByPrimaryKey(RequestThread.get().getUid()), "重置了id=" + id + "账户的密码");
			managerService.updateByPrimaryKeySelective(manager);
			return new ResultMessage(ResponseEnum.M2000, password);
		} catch (Exception e) {
			logger.error("账户重置密码失败,error_msg={}", e.getMessage());
		}
		return new ResultMessage(ResponseEnum.M4000);
	}

	/**
	 * 新增manager时，验证登录账号与工号是否唯一
	 * 
	 * @param manager
	 * @return
	 */
	@RequestMapping(value = "manager_varify_ajax", method = { RequestMethod.POST })
	public ResultMessage manager_varify_ajax(Manager manager) {
		if (!StringUtils.isEmpty(manager.getLoginName()) || !StringUtils.isEmpty(manager.getUserWorkNumber())) {
			if (managerService.selectCount(manager) != 0) {
				return new ResultMessage(ResponseEnum.M2000);
			}
		}
		return new ResultMessage(ResponseEnum.M4000);
	}
	
	/**
	 * 操作账号的日志打印
	 * 
	 * @param request
	 */
	private static void loggerManagerOperation(HttpServletRequest request, Manager manager, String operation) {
		String ip = HttpUtils.getIpAddr(request, ".");
		logger.info("账户信息的操作日志,ip={},loginname={}," + operation, ip, manager.getLoginName());
	}
}
