package com.mod.loan.service;

import java.util.List;

import com.mod.loan.common.mapper.BaseService;
import com.mod.loan.model.MerchantResource;

public interface MerchantResourceService extends BaseService<MerchantResource, Long> {
	/**
	 * 查找菜单
	 * 
	 * @param parentId
	 *            父ID 等于0时为模块菜单
	 * @param status
	 *            状态 null-所有，0-正常，1-停用
	 * @return
	 */
	List<MerchantResource> findMerchantResourceList(Long parentId, Integer status);

}
