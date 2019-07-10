package com.mod.loan.service.impl;

import com.mod.loan.common.model.Page;
import com.mod.loan.mapper.MerchantFeeStatisticsMapper;
import com.mod.loan.mapper.OrderRiskInfoMapper;
import com.mod.loan.mapper.SmsRecordMapper;
import com.mod.loan.mapper.ThirdCallHistoryMapper;
import com.mod.loan.model.MerchantFee;
import com.mod.loan.service.MerchantFeeStatisticsService;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author actor
 * @date 2019/7/3 20:43
 */
@Service
public class MerchantFeeStatisticsServiceImpl implements MerchantFeeStatisticsService {
    @Resource
    private MerchantFeeStatisticsMapper merchantFeeStatisticsMapper;
    @Resource
    private SmsRecordMapper smsRecordMapper;
    @Resource
    private ThirdCallHistoryMapper thirdCallHistoryMapper;
    @Resource
    private OrderRiskInfoMapper orderRiskInfoMapper;


    @Override
    public List<Map<String, Object>> findMerchantFeeStatisticsList(String startTime, String endTime, Page page) {
        Map<String, Object> params = new HashMap<>();
        if (StringUtils.isNotEmpty(startTime)) {
            params.put("startTime", DateUtil.parseYYYYMMDDDate(startTime));
        }
        if (StringUtils.isNotEmpty(endTime)) {
            params.put("endTime", DateUtil.parseYYYYMMDDDate(endTime));
        }
        params.put("startIndex", page.getStartIndex());
        params.put("pageSize", page.getPageSize());
        page.setTotalCount(merchantFeeStatisticsMapper.findMerchantFeeStatisticsCount(params));
        List<Map<String, Object>> merchantFeeStatisticsList = merchantFeeStatisticsMapper.findMerchantFeeStatisticsList(params);
        //第一页
        if (page.getStartIndex() == 0) {
            Map<String, Object> sumMerchantFeeStatistics = merchantFeeStatisticsMapper.findSumMerchantFeeStatistics(params);
            merchantFeeStatisticsList.add(0, sumMerchantFeeStatistics);
        }
        return merchantFeeStatisticsList;
    }

    @Override
    public List<Map<String, Object>> findMerchantFeeStatisticsDetail(String time, Page page) {
        Map<String, Object> params = new HashMap<>();
        params.put("time", time);
        params.put("startIndex", page.getStartIndex());
        params.put("pageSize", page.getPageSize());
        page.setTotalCount(merchantFeeStatisticsMapper.findMerchantFeeStatisticsDetailCount(params));
        List<Map<String, Object>> merchantFeeStatisticsList = merchantFeeStatisticsMapper.findMerchantFeeStatisticsDetail(params);
        //第一页
        if (page.getStartIndex() == 0) {
            Map<String, Object> sumMerchantFeeStatistics = merchantFeeStatisticsMapper.findSumMerchantFeeStatisticsDetail(params);
            merchantFeeStatisticsList.add(0, sumMerchantFeeStatistics);
        }
        return merchantFeeStatisticsList;
    }

    @Override
    public Map<String, Integer> merchantNowStatisticsCount(Date now) {
        Map<String,Integer> result=new HashMap<>();
        Map smsMap=new HashMap();
        smsMap.put("now",now);
        smsMap.put("channel",1);
        result.put("sms1Count",smsRecordMapper.selectCountNow(smsMap));
        smsMap.put("channel",2);
        result.put("sms2Count",smsRecordMapper.selectCountNow(smsMap));
        Map youdunMap=new HashMap();
        youdunMap.put("now",now);
        youdunMap.put("code",1);
        Integer thirdCall = thirdCallHistoryMapper.selectCountNow(youdunMap);
        result.put("youdunCount",thirdCall==null?0:thirdCall);
        youdunMap.put("code",2);
        Integer thirdCall2 = thirdCallHistoryMapper.selectCountNow(youdunMap);
        result.put("operatorCount",thirdCall2==null?0:thirdCall2);
        Map riskMap=new HashMap();
        riskMap.put("now",now);
        result.put("riskCount",orderRiskInfoMapper.selectCountNow(riskMap));
        riskMap.put("riskStatus",2);
        result.put("refusedCount",orderRiskInfoMapper.selectCountNow(riskMap));

        return result;
    }
}
