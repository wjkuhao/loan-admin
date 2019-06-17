package com.mod.loan.service;

import com.mod.loan.common.mapper.BaseService;
import com.mod.loan.common.model.Page;
import com.mod.loan.model.MerchantQuotaConfig;

import java.util.List;
import java.util.Map;

public interface MerchantQuotaConfigService extends BaseService<MerchantQuotaConfig, Long> {
    List<Map<String, Object>> findMerchantQuotaConfigList(Map<String, Object> param, Page page);
}
