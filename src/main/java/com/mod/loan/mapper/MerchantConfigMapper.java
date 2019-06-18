package com.mod.loan.mapper;

import com.mod.loan.common.mapper.MyBaseMapper;
import com.mod.loan.common.model.Page;
import com.mod.loan.model.MerchantConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface MerchantConfigMapper extends MyBaseMapper<MerchantConfig> {
    List<Map<String,Object>> findMerchantConfigList(Map<String,Object> params);
    int findMerchantConfigCount(Map<String,Object> params);
}
