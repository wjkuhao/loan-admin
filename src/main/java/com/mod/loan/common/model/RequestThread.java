package com.mod.loan.common.model;

/**
 * 
 *  @author wugy
 *  2018年7月15日  下午7:03:43
 */
public class RequestThread {
	private static final ThreadLocal<RequestBean> requestThread = new ThreadLocal<RequestBean>();

	public static RequestBean get() {
		RequestBean s = requestThread.get();
		if (s == null) {
			s = new RequestBean();
			requestThread.set(s);
		}
		return s;
	}

	public static void remove() {
		requestThread.remove();
	}

}
