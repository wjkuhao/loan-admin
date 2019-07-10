package com.mod.loan.controller.statistics;

import com.mod.loan.common.enums.ResponseEnum;
import com.mod.loan.common.model.Page;
import com.mod.loan.common.model.ResultMessage;
import com.mod.loan.controller.merchant.MerchantFeeController;
import com.mod.loan.mapper.MerchantFeeStatisticsMapper;
import com.mod.loan.model.MerchantFee;
import com.mod.loan.service.MerchantFeeStatisticsService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

/**
 * @author actor
 * @date 2019/7/3 20:32
 */
@RestController
@RequestMapping("statistics")
public class StatisticsFeeController {
    public static final Logger logger = LoggerFactory.getLogger(StatisticsFeeController.class);

    @Autowired
    private MerchantFeeStatisticsService merchantFeeStatisticsService;

    @RequestMapping(value = "merchant_fee_statistics_list")
    public ModelAndView merchant_fee_statistics_list(ModelAndView view) {
        view.setViewName("statistics/merchant_fee_statistics_list");
        return view;
    }

    @RequestMapping(value = "merchant_fee_statistics_detail")
    public ModelAndView merchant_fee_statistics_detail(ModelAndView view, String time) {
        view.setViewName("statistics/merchant_fee_statistics_detail");
        view.addObject("time", time);
        return view;
    }

    // 当前时间数据条数
    @RequestMapping(value = "/merchant_now_statistics_count_ajax", method = {RequestMethod.POST})
    public ResultMessage merchant_now_statistics_count_ajax() {
        Date now = new Date();
        return new ResultMessage(ResponseEnum.M2000, merchantFeeStatisticsService.merchantNowStatisticsCount(now));
    }

    // 查找全部商户费用统计
    @RequestMapping(value = "/merchant_fee_statistics_list_ajax", method = {RequestMethod.POST})
    public ResultMessage merchant_fee_statistics_list_ajax(String startTime, String endTime, Page page) {
        return new ResultMessage(ResponseEnum.M2000, merchantFeeStatisticsService.findMerchantFeeStatisticsList(startTime, endTime, page), page);
    }

    // 查找全部商户费用统计
    @RequestMapping(value = "/merchant_fee_statistics_detail_ajax", method = {RequestMethod.POST})
    public ResultMessage merchant_fee_statistics_detail_ajax(String time, Page page) {
        if (StringUtils.isEmpty(time)) {
            return new ResultMessage(ResponseEnum.M4000.getCode(), "时间为空");
        }
        return new ResultMessage(ResponseEnum.M2000, merchantFeeStatisticsService.findMerchantFeeStatisticsDetail(time, page), page);
    }

}
