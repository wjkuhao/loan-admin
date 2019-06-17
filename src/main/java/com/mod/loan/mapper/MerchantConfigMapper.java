package com.mod.loan.mapper;

import com.mod.loan.common.mapper.MyBaseMapper;
import com.mod.loan.common.model.Page;
import com.mod.loan.model.MerchantConfig;

import java.util.HashMap;
import java.util.List;

public interface MerchantConfigMapper extends MyBaseMapper<MerchantConfig> {
    List<MerchantConfig> findMerchantConfigList(HashMap<String,Object> params);
}
