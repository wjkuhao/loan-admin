package com.mod.loan.service.impl;

import com.mod.loan.common.mapper.BaseServiceImpl;
import com.mod.loan.common.model.Page;
import com.mod.loan.mapper.MerchantFeeMapper;
import com.mod.loan.model.MerchantFee;
import com.mod.loan.service.MerchantFeeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MerchantFeeServiceImpl extends BaseServiceImpl<MerchantFee, String> implements MerchantFeeService {

    @Resource
    private MerchantFeeMapper merchantFeeMapper;

    @Override
    public List<Map<String, Object>> findMerchantFeeList(MerchantFee merchantFee, Page page) {
        Map<String, Object> params = new HashMap<>();
        params.put("merchantAlias", merchantFee.getMerchantAlias());
        params.put("startIndex", page.getStartIndex());
        params.put("pageSize", page.getPageSize());
        page.setTotalCount(merchantFeeMapper.findMerchantFeeCount(params));
        return merchantFeeMapper.findMerchantFeeList(params);
    }

    @Override
    public List<String> merchantNofeeList() {
        return merchantFeeMapper.merchantNofeeList();
    }

    @Override
    public int merchantFeeAdd(MerchantFee merchantFee) {
        return merchantFeeMapper.insert(merchantFee);
    }

    @Override
    public int merchantFeeEdit(MerchantFee merchantFee) {
        return merchantFeeMapper.updateByPrimaryKeySelective(merchantFee);
    }
}