package com.mod.loan.service.impl;

import com.mod.loan.common.mapper.BaseServiceImpl;
import com.mod.loan.common.model.Page;
import com.mod.loan.mapper.MerchantConfigMapper;
import com.mod.loan.model.MerchantConfig;
import com.mod.loan.service.MerchantConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("merchantConfigService")
public class MerchantConfigImpl extends BaseServiceImpl<MerchantConfig, Integer> implements MerchantConfigService {
    @Autowired
    private MerchantConfigMapper merchantConfigMapper;

    @Override
    public List<Map<String, Object>> findMerchantConfigList(MerchantConfig merchantConfig, Page page) {
        Map<String,Object> params = new HashMap<>();
        params.put("autoApplyOrder",merchantConfig.getAutoApplyOrder());
        params.put("merchant", merchantConfig.getMerchant());
        params.put("startIndex",page.getStartIndex());
        params.put("pageSize",page.getPageSize());
        page.setTotalCount(merchantConfigMapper.findMerchantConfigCount(params));
        return merchantConfigMapper.findMerchantConfigList(params);
    }
}
