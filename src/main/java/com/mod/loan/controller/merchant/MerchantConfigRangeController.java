package com.mod.loan.controller.merchant;

import com.mod.loan.common.enums.ResponseEnum;
import com.mod.loan.common.model.ResultMessage;
import com.mod.loan.model.MerchantConfigRange;
import com.mod.loan.model.MerchantFee;
import com.mod.loan.service.MerchantConfigRangeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

/**
 * @author actor
 * @date 2019/7/5 11:05
 */
@RestController
@RequestMapping("merchant")
public class MerchantConfigRangeController {

    @Autowired
    private MerchantConfigRangeService merchantConfigRangeService;


    @RequestMapping(value = "merchant_config_range_edit")
    public ModelAndView merchant_list(ModelAndView view,String merchantAlias) {
        view.setViewName("merchant/merchant_config_range_edit");
        MerchantConfigRange merchantConfigRange = merchantConfigRangeService.selectByPrimaryKey(merchantAlias);
        view.addObject("merchantAlias",merchantAlias);
        if(merchantConfigRange!=null){
            view.addObject("productDayMax",merchantConfigRange.getProductDayMax());
            view.addObject("productDayMin",merchantConfigRange.getProductDayMin());
            view.addObject("productMoneyMax",merchantConfigRange.getProductDayMax());
            view.addObject("productMoneyMin",merchantConfigRange.getProductMoneyMin());
            view.addObject("totalRateMax",merchantConfigRange.getTotalRateMax());
            view.addObject("totalRateMin",merchantConfigRange.getTotalRateMin());
            view.addObject("overdueRateMin",merchantConfigRange.getOverdueRateMin());
            view.addObject("overdueRateMax",merchantConfigRange.getOverdueRateMax());
            view.addObject("dailyDeferFeeMin",merchantConfigRange.getDailyDeferFeeMin());
            view.addObject("dailyDeferFeeMax",merchantConfigRange.getDailyDeferFeeMax());
            view.addObject("dailyDeferRateMin",merchantConfigRange.getDailyDeferRateMin());
            view.addObject("dailyDeferRateMax",merchantConfigRange.getDailyDeferRateMax());
            view.addObject("dailyOtherFeeMin",merchantConfigRange.getDailyOtherFeeMin());
            view.addObject("dailyOtherFeeMax",merchantConfigRange.getDailyOtherFeeMax());
            view.addObject("deferDayMin",merchantConfigRange.getDeferDayMin());
            view.addObject("deferDayMax",merchantConfigRange.getDeferDayMax());
        }
        return view;
    }

    /**
     * 新增加修改
     *
     * @author actor
     * @date 2019/7/3 17:35
     */
    @RequestMapping(value = "merchant_config_range_edit_ajax", method = RequestMethod.POST)
    public ResultMessage merchant_config_range_edit_ajax(MerchantConfigRange merchantConfigRange) {
        if(StringUtils.isEmpty(merchantConfigRange.getMerchantAlias())){
            return new ResultMessage(ResponseEnum.M4000.getCode(), "商户号为空");
        }
        MerchantConfigRange merchantConfigRange1 = merchantConfigRangeService.selectByPrimaryKey(merchantConfigRange.getMerchantAlias());
        if (merchantConfigRange1 != null) {
            merchantConfigRange.setUpdateTime(new Date());
            merchantConfigRangeService.updateByPrimaryKeySelective(merchantConfigRange);
            return new ResultMessage(ResponseEnum.M2000);
        }
        merchantConfigRange.setCreateTime(new Date());
        merchantConfigRangeService.insertSelective(merchantConfigRange);
        return new ResultMessage(ResponseEnum.M2000);
    }


}
