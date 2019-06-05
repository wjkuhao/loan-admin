package com.mod.loan.service;

import java.util.List;
import java.util.Map;

import com.mod.loan.common.mapper.BaseService;
import com.mod.loan.common.model.Page;
import com.mod.loan.model.Merchant;

public interface MerchantService extends BaseService<Merchant, String> {

	List<Map<String, Object>> findMerchantList(Merchant merchant, Page page);

	/**
	 * 新增商户时为商户新增一个用于后台系统登录的管理员账号
	 * 
	 * @param merchant
	 */
	void insertMerchantAndMerchantManager(Merchant merchant,String userPhone);

	/**
	 * 新设置的值不为空并且和原来的值不相等才更新值
	 * @param oldKey 旧值
	 * @param newKey 新值
	 * @return 加密后的数据
	 */
	String encodeKey(String oldKey, String newKey);
}
