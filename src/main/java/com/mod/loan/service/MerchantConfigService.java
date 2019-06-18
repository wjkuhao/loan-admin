package com.mod.loan.service;

import com.mod.loan.common.mapper.BaseService;
import com.mod.loan.common.model.Page;
import com.mod.loan.model.MerchantConfig;

import java.util.List;
import java.util.Map;

public interface MerchantConfigService extends BaseService<MerchantConfig, Integer> {
    List<Map<String,Object>> findMerchantConfigList(MerchantConfig merchantConfig, Page page);
}
