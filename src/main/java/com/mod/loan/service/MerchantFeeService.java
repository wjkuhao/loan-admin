package com.mod.loan.service;

import com.mod.loan.common.mapper.BaseService;
import com.mod.loan.common.model.Page;
import com.mod.loan.model.MerchantFee;
import com.mod.loan.model.MerchantRate;

import java.util.List;
import java.util.Map;

public interface MerchantFeeService extends BaseService<MerchantFee,String>{

    List<Map<String, Object>> findMerchantFeeList(MerchantFee merchantFee, Page page);

    List<String> merchantNofeeList();

    int merchantFeeAdd(MerchantFee merchantFee);

    int merchantFeeEdit(MerchantFee merchantFee);
}
