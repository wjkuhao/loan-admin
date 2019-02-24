package com.mod.loan.config.redis;

/**
 * 
 * @author wugy 2018年6月26日 下午3:09:27
 */
public interface RedisConst {
	String USER_TOKEN = "user:token:";
	String USER_RIGHT = "user:right:";
	String SINGLE_DEVICE_LOGIN_FLAG = "single:device:login:flag:";// 单设备登录次数记录
	String LOCK_ILLEGAL_IP = "lock:illegal:ip:";// 非法ip
	String USER_SECURITY_CODE = "user:security:code:";// ip验证未通过后的验证码
	String PASSWORD_ERROR_FLAG = "password:error:";
}
