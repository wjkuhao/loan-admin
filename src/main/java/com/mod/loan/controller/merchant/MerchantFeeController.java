package com.mod.loan.controller.merchant;

import com.mod.loan.common.enums.ResponseEnum;
import com.mod.loan.common.model.Page;
import com.mod.loan.common.model.ResultMessage;
import com.mod.loan.model.MerchantFee;
import com.mod.loan.service.MerchantFeeService;
import com.mod.loan.service.MerchantRateService;
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
 * 商户费用限制
 * @author actor
 * @date 2019/7/3 15:52
 */
@RestController
@RequestMapping("merchant")
public class MerchantFeeController {
    public static final Logger logger = LoggerFactory.getLogger(MerchantFeeController.class);

    @Autowired
    private MerchantFeeService merchantFeeService;

    @RequestMapping(value = "merchant_fee_list")
    public ModelAndView merchant_list(ModelAndView view) {
        view.setViewName("merchant/merchant_fee_list");
        return view;
    }


    @RequestMapping(value = "merchant_fee_add")
    public ModelAndView merchant_fee_add(ModelAndView view) {
        view.setViewName("merchant/merchant_fee_add");
        return view;
    }

    @RequestMapping(value = "merchant_fee_edit")
    public ModelAndView merchant_fee_add(ModelAndView view, String merchantAlias) {
        view.setViewName("merchant/merchant_fee_edit");
        MerchantFee merchantFee = merchantFeeService.selectByPrimaryKey(merchantAlias);
        if (merchantFee != null) {
            view.addObject("sms1Price", merchantFee.getSms1Price());
            view.addObject("sms2Price", merchantFee.getSms2Price());
            view.addObject("operatorPrice", merchantFee.getOperatorPrice());
            view.addObject("riskPrice", merchantFee.getRiskPrice());
            view.addObject("youdunPrice", merchantFee.getYoudunPrice());
        }
        view.addObject("merchantAlias", merchantAlias);
        return view;
    }


    // 查找全部商户费用配置
    @RequestMapping(value = "/merchant_fee_list_ajax", method = {RequestMethod.POST})
    public ResultMessage merchant_congfig_list_ajax(MerchantFee merchantFee, Page page) {
        return new ResultMessage(ResponseEnum.M2000, merchantFeeService.findMerchantFeeList(merchantFee, page), page);
    }

    /**
     * 未配置的商户别名
     *
     * @author actor
     * @date 2019/7/3 17:09
     */
    @RequestMapping(value = "merchant_nofee_list_ajax", method = RequestMethod.POST)
    public ResultMessage merchant_nofee_list_ajax() {
        return new ResultMessage(ResponseEnum.M2000, merchantFeeService.merchantNofeeList());
    }

    /**
     * 新增
     *
     * @author actor
     * @date 2019/7/3 17:35
     */
    @RequestMapping(value = "merchant_fee_add_ajax", method = RequestMethod.POST)
    public ResultMessage merchant_fee_add_ajax(MerchantFee merchantFee) {
        if(StringUtils.isEmpty(merchantFee.getMerchantAlias())){
            return new ResultMessage(ResponseEnum.M4000.getCode(), "商户号为空");
        }
        MerchantFee merchantFee1 = merchantFeeService.selectByPrimaryKey(merchantFee.getMerchantAlias());
        if (merchantFee1 != null) {
            return new ResultMessage(ResponseEnum.M4000.getCode(), "该商户已配置，请勿重复配置");
        }
        merchantFee.setCreateTime(new Date());
        merchantFeeService.merchantFeeAdd(merchantFee);
        return new ResultMessage(ResponseEnum.M2000);
    }

    /**
     * 编辑
     *
     * @author actor
     * @date 2019/7/3 17:35
     */
    @RequestMapping(value = "merchant_fee_edit_ajax", method = RequestMethod.POST)
    public ResultMessage merchant_fee_edit_ajax(MerchantFee merchantFee) {
        if(StringUtils.isEmpty(merchantFee.getMerchantAlias())){
            return new ResultMessage(ResponseEnum.M4000.getCode(), "商户号为空");
        }
        merchantFee.setUpdateTime(new Date());
        merchantFeeService.merchantFeeEdit(merchantFee);
        return new ResultMessage(ResponseEnum.M2000);
    }
}
