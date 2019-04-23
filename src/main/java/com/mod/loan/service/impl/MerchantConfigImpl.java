package com.mod.loan.service.impl;

import com.mod.loan.common.mapper.BaseServiceImpl;
import com.mod.loan.model.MerchantConfig;
import com.mod.loan.service.MerchantConfigService;
import org.springframework.stereotype.Service;

@Service("merchantConfigService")
public class MerchantConfigImpl extends BaseServiceImpl<MerchantConfig, Integer> implements MerchantConfigService {
}
