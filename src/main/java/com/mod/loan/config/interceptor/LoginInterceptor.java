package com.mod.loan.config.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.mod.loan.common.model.RequestBean;
import com.mod.loan.common.model.RequestThread;
import com.mod.loan.config.Constant;
import com.mod.loan.config.redis.RedisConst;
import com.mod.loan.config.redis.RedisMapper;
import com.mod.loan.util.CookieUtils;
import com.mod.loan.util.HttpUtils;
import com.mod.loan.util.MD5;
import com.mod.loan.util.JwtUtil;

import io.jsonwebtoken.Claims;

/**
 * 
 * @author wugy 2018年7月15日 下午8:54:42
 */
public class LoginInterceptor implements HandlerInterceptor {

	private static Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

	@Autowired
	RedisMapper redisMapper;

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object obj, Exception exception) throws Exception {
		RequestThread.remove();// 移除本地线程变量
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object obj, ModelAndView mv) throws Exception {

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
		RequestThread.remove();
		String token = CookieUtils.getCookieValue(request, Constant.cookie_token);
		String ip = HttpUtils.getIpAddr(request, ".");
		String ua = request.getHeader("User-Agent");
		String host = request.getHeader("Host");

		// 非法ip拒绝请求
		if (redisMapper.get(RedisConst.LOCK_ILLEGAL_IP + ip) != null) {
			PrintWriter out = response.getWriter();
			out.println("System busy, please try again later!");
			out.flush();
			out.close();
			return false;
		}

		if (StringUtils.isBlank(token)) {
			response.sendRedirect(request.getContextPath() + "/system/login");
			return false;
		}
		String key = MD5.toMD5(String.format("%s:%s:%s", host, ip, ua));
		Claims claims = JwtUtil.ParseJwt(token);
		if (claims == null) {
			logger.error("token解析异常,ip={},url={},", ip, request.getRequestURI());
			response.sendRedirect(request.getContextPath() + "/system/login");
			return false;
		}
		String uid = String.valueOf(claims.get("uid"));
		String mykey = String.valueOf(claims.get("key"));
		if (!key.equals(mykey)) {
			logger.error("token单设备登录,uid={},ip={},url={},", uid, ip, request.getRequestURI());
			long increment = redisMapper.increment(RedisConst.SINGLE_DEVICE_LOGIN_FLAG + ":" + ip, 1L, 3600L);
			if (increment > 10) {// 单设备登录次数过多
				//redisMapper.set(RedisConst.LOCK_ILLEGAL_IP + ip, ip, 3600L);
			}
			response.sendRedirect(request.getContextPath() + "/system/login");
			return false;
		}
		String mytoken = redisMapper.get(RedisConst.USER_TOKEN + uid);
		if (!token.equals(mytoken)) {
			logger.error("token过期或者非法,uid={},ip={},url={},", uid, ip, request.getRequestURI());
			response.sendRedirect(request.getContextPath() + "/system/login");
			return false;
		}
		RequestBean requestBean = RequestThread.get();
		requestBean.setHost(host);
		requestBean.setIp(ip);
		requestBean.setToken(token);
		requestBean.setUa(ua);
		requestBean.setUid(Long.valueOf(uid));
		redisMapper.set(RedisConst.USER_TOKEN + uid, token, 7200);// 刷新token
		return true;
	}

}
