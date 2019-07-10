package com.mod.loan.mapper;


import com.mod.loan.model.MerchantFeeStatistics;

import java.util.List;
import java.util.Map;

public interface MerchantFeeStatisticsMapper {
    int deleteByPrimaryKey(MerchantFeeStatistics key);

    int insert(MerchantFeeStatistics record);

    int insertSelective(MerchantFeeStatistics record);

    MerchantFeeStatistics selectByPrimaryKey(MerchantFeeStatistics key);

    int updateByPrimaryKeySelective(MerchantFeeStatistics record);

    int updateByPrimaryKey(MerchantFeeStatistics record);

    int findMerchantFeeStatisticsCount(Map<String, Object> params);

    List<Map<String, Object>> findMerchantFeeStatisticsList(Map<String, Object> params);

    Map<String, Object> findSumMerchantFeeStatistics(Map<String, Object> params);

    int findMerchantFeeStatisticsDetailCount(Map<String, Object> params);

    List<Map<String, Object>> findMerchantFeeStatisticsDetail(Map<String, Object> params);

    Map<String, Object> findSumMerchantFeeStatisticsDetail(Map<String, Object> params);
}