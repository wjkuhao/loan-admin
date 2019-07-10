package com.mod.loan.mapper;

import com.mod.loan.common.mapper.MyBaseMapper;
import com.mod.loan.model.MerchantFee;

import java.util.List;
import java.util.Map;

public interface MerchantFeeMapper extends MyBaseMapper<MerchantFee> {
    int findMerchantFeeCount(Map<String, Object> params);

    List<Map<String, Object>> findMerchantFeeList(Map<String, Object> params);

    List<String> merchantNofeeList();
}