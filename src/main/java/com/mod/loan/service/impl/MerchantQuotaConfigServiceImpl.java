package com.mod.loan.service.impl;

import com.mod.loan.common.mapper.BaseServiceImpl;
import com.mod.loan.common.model.Page;
import com.mod.loan.config.Constant;
import com.mod.loan.mapper.LoginRecordMapper;
import com.mod.loan.mapper.ManagerMapper;
import com.mod.loan.mapper.MerchantQuotaConfigMapper;
import com.mod.loan.model.Manager;
import com.mod.loan.model.MerchantQuotaConfig;
import com.mod.loan.service.ManagerService;
import com.mod.loan.service.MerchantQuotaConfigService;
import com.mod.loan.util.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author wgy
 *
 */
@Service
public class MerchantQuotaConfigServiceImpl extends BaseServiceImpl<MerchantQuotaConfig, Long> implements MerchantQuotaConfigService {

	private static final Logger logger = LoggerFactory.getLogger(MerchantQuotaConfigServiceImpl.class);
	
	@Autowired
	private MerchantQuotaConfigMapper merchantQuotaConfigMapper;


	@Override
	public List<Map<String, Object>> findMerchantQuotaConfigList(Map<String, Object> param, Page page) {
		param.put("startIndex", page.getStartIndex());
		param.put("pageSize", page.getPageSize());
		page.setTotalCount(merchantQuotaConfigMapper.MerchantQuotaConfigCount(param));
		return merchantQuotaConfigMapper.findMerchantQuotaConfigList(param);
	}
}
