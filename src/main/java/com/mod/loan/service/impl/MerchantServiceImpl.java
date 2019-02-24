package com.mod.loan.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mod.loan.common.mapper.BaseServiceImpl;
import com.mod.loan.common.model.Page;
import com.mod.loan.mapper.MerchantManagerMapper;
import com.mod.loan.mapper.MerchantMapper;
import com.mod.loan.model.Merchant;
import com.mod.loan.model.MerchantManager;
import com.mod.loan.service.MerchantService;
import com.mod.loan.util.MD5;

@Service
public class MerchantServiceImpl extends BaseServiceImpl<Merchant, String> implements MerchantService {

	private String password = "123456";

	@Autowired
	private MerchantMapper merchantMapper;
	@Autowired
	private MerchantManagerMapper merchantManagerMapper;

	@Override
	public List<Map<String, Object>> findMerchantList(Merchant merchant, Page page) {
		Map<String, Object> param = new HashMap<String, Object>();

		param.put("merchantAlias", StringUtils.isNotEmpty(merchant.getMerchantAlias()) ? merchant.getMerchantAlias() : null);
		param.put("merchantStatus", merchant.getMerchantStatus());
		param.put("startIndex", page.getStartIndex());
		param.put("pageSize", page.getPageSize());
		page.setTotalCount(merchantMapper.merchantCount(param));
		return merchantMapper.findMerchantList(param);
	}

	@Override
	public void insertMerchantAndMerchantManager(Merchant merchant, String userPhone) {
		MerchantManager merchantManager = new MerchantManager();
		merchantManager.setLoginName(merchant.getMerchantAlias());
		merchantManager.setLoginPassword(MD5.toMD5(password));
		merchantManager.setMerchant(merchant.getMerchantAlias());
		merchantManager.setRoleId(null);
		merchantManager.setUserName(merchant.getMerchantAlias());
		merchantManager.setUserPhone(userPhone);
		merchantManager.setAccountStatus(0);
		// 管理员权限
		merchantManager.setAccountType(1);
		merchantManagerMapper.insertSelective(merchantManager);
		merchantMapper.insertSelective(merchant);
	}

}
