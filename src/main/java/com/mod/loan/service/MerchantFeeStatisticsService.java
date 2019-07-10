package com.mod.loan.service;

import com.mod.loan.common.mapper.BaseService;
import com.mod.loan.common.model.Page;
import com.mod.loan.model.MerchantFee;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface MerchantFeeStatisticsService{

    List<Map<String, Object>> findMerchantFeeStatisticsList(String startTime, String endTime, Page page);

    List<Map<String, Object>> findMerchantFeeStatisticsDetail(String time, Page page);

    Map<String, Integer> merchantNowStatisticsCount(Date now);
}
