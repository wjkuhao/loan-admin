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

}
