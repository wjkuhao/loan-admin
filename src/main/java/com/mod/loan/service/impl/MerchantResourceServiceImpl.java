package com.mod.loan.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mod.loan.common.mapper.BaseServiceImpl;
import com.mod.loan.mapper.MerchantResourceMapper;
import com.mod.loan.model.MerchantResource;
import com.mod.loan.service.MerchantResourceService;

@Service
public class MerchantResourceServiceImpl extends BaseServiceImpl<MerchantResource, Long> implements MerchantResourceService {
	@Autowired
	private MerchantResourceMapper merchantResourceMapper;

	@Override
	public List<MerchantResource> findMerchantResourceList(Long parentId, Integer status) {
		MerchantResource merchantResource = new MerchantResource();
		merchantResource.setPid(parentId);
		merchantResource.setResourceStatus(status);
		return merchantResourceMapper.findMerchantResourceList(merchantResource);
	}
}
