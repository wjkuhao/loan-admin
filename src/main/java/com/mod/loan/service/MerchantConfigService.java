package com.mod.loan.service;

import com.mod.loan.common.mapper.BaseService;
import com.mod.loan.common.model.Page;
import com.mod.loan.model.MerchantConfig;

import java.util.List;

public interface MerchantConfigService extends BaseService<MerchantConfig, Integer> {
    List<MerchantConfig> findMerchantConfigList(MerchantConfig merchantConfig, Page page);
}
