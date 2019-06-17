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

@Service("merchantConfigService")
public class MerchantConfigImpl extends BaseServiceImpl<MerchantConfig, Integer> implements MerchantConfigService {
    @Autowired
    private MerchantConfigMapper merchantConfigMapper;
    @Override
    public List<MerchantConfig> findMerchantConfigList(MerchantConfig merchantConfig, Page page) {
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("autoApplyOrder",merchantConfig.getAutoApplyOrder());
        hashMap.put("merchant",merchantConfig.getMerchant());
        hashMap.put("startIndex",page.getStartIndex());
        hashMap.put("pageSize",page.getPageSize());
        page.setTotalCount(merchantConfigMapper.selectCount(merchantConfig));
        return merchantConfigMapper.findMerchantConfigList(hashMap);
    }
}
