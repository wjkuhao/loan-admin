package com.mod.loan.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	/**
	 * 校验密码，必须由6~18位数字、字母、特殊符号(.!@#$%&*)组成
	 * 
	 * @param password
	 * @return
	 */
	public static boolean verifyPassword(String password) {
		String pattern = "^(?!^[0-9]+$)(?!^[a-zA-Z]+$)(?!^[!@#$%&*]+$).{6,18}$";
		if (password == null || password.isEmpty()) {
			return false;
		} else {
			return Pattern.matches(pattern, password);
		}
	}

	/**
	 * 手机号通用判断
	 *
	 * @param telNum
	 * @return
	 */
	public static boolean isMobiPhoneNum(String telNum) {
		if (telNum == null || telNum.isEmpty()) {
			return false;
		}
		String regex = "^((13[0-9])|(14[0-9])|(15[0-9])|(16[0-9])|(17[0-9])|(18[0-9])|(19[0-9]))\\d{8}$";
		Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(telNum);
		return m.matches();
	}

	/**
	 *
	 * 手机号脱敏
	 * 
	 * @param phone
	 * @return
	 */
	public static String phoneReplaceWithStar(String phone) {
		if (phone.isEmpty() || phone == null) {
			return null;
		} else {
			return replaceAction(phone, "(?<=\\d{3})\\d(?=\\d{4})");
		}
	}

	/**
	 * 实际替换动作
	 *
	 * @param username
	 *            username
	 * @param regular
	 *            正则
	 * @return
	 */
	private static String replaceAction(String username, String regular) {
		return username.replaceAll(regular, "*");
	}

}
