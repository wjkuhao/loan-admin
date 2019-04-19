package com.mod.loan.controller.merchant;

import com.mod.loan.common.enums.ResponseEnum;
import com.mod.loan.common.model.Page;
import com.mod.loan.common.model.ResultMessage;
import com.mod.loan.model.Merchant;
import com.mod.loan.service.MerchantService;
import com.mod.loan.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value = "merchant")
public class MerchantController {

    public static final Logger logger = LoggerFactory.getLogger(MerchantController.class);

    @Autowired
    private MerchantService merchantService;

    @RequestMapping(value = "merchant_list")
    public ModelAndView merchant_list(ModelAndView view) {
        view.setViewName("merchant/merchant_list");
        return view;
    }

    @RequestMapping(value = "merchant_list_ajax", method = {RequestMethod.POST})
    public ResultMessage merchant_list_ajax(Merchant merchant, Page page) {
        return new ResultMessage(ResponseEnum.M2000, merchantService.findMerchantList(merchant, page), page);
    }

    @RequestMapping(value = "merchant_edit")
    public ModelAndView merchant_edit(ModelAndView view, String merchantAlias) {
        if (StringUtils.isEmpty(merchantAlias)) {
            view.setViewName("merchant/merchant_add");
        } else {
            view.addObject("merchantAlias", merchantAlias);
            view.setViewName("merchant/merchant_edit");
        }
        return view;
    }

    @RequestMapping(value = "merchant_edit_ajax", method = {RequestMethod.POST})
    public ResultMessage merchant_edit_ajax(Merchant merchant, String flag, String merchantChannel, String userPhone) {
        if (StringUtils.isBlank(merchant.getMerchantAlias())) {
            return new ResultMessage(ResponseEnum.M4000.getCode(), "商户别名不能为空");
        }
        if (StringUtils.isBlank(merchant.getMerchantApp())) {
            return new ResultMessage(ResponseEnum.M4000.getCode(), "app名称不能为空");
        }
        if (StringUtils.isBlank(merchant.getMerchantName())) {
            return new ResultMessage(ResponseEnum.M4000.getCode(), "商户公司名称不能为空");
        }
        if (merchant.getBindType() != 1 && merchant.getBindType() != 2 && merchant.getBindType() != 3 && merchant.getBindType() != 4) {
            return new ResultMessage(ResponseEnum.M4000.getCode(), "不支持的绑卡类型");
        }
        if ("add".equals(flag) && null != merchantService.selectByPrimaryKey(merchant.getMerchantAlias())) {
            return new ResultMessage(ResponseEnum.M4000.getCode(), "该商户别名已存在");
        }
        if ("add".equals(flag) && StringUtils.isBlank(userPhone)) {
            return new ResultMessage(ResponseEnum.M4000.getCode(), "商户手机号不能为空");
        }
        if ("add".equals(flag) && !StringUtil.isMobiPhoneNum(userPhone)) {
            return new ResultMessage(ResponseEnum.M4000.getCode(), "请输入正确的手机号码");
        }
        if ("add".equals(flag)) {
            merchant.setMerchantChannel(merchantChannel);
            merchantService.insertMerchantAndMerchantManager(merchant, userPhone);
        } else {
            merchant.setMerchantChannel(merchantChannel);
            merchantService.updateByPrimaryKeySelective(merchant);
        }
        return new ResultMessage(ResponseEnum.M2000);
    }

    @RequestMapping(value = "merchant_detail_ajax", method = {RequestMethod.POST})
    public ResultMessage merchant_detail_ajax(String merchantAlias) {
        return new ResultMessage(ResponseEnum.M2000, merchantService.selectByPrimaryKey(merchantAlias));
    }

    @RequestMapping(value = "merchant_all_list_ajax", method = {RequestMethod.POST})
    public ResultMessage merchant_all_list_ajax() {
        Merchant merchant = new Merchant();
        merchant.setMerchantStatus(1);
        return new ResultMessage(ResponseEnum.M2000, merchantService.select(merchant));
    }

    @RequestMapping(value = "edit_helibao_channel")
    public ModelAndView edit_merchant_channel(ModelAndView view, String merchantAlias) {
        view.addObject("merchantAlias", merchantAlias);
        view.setViewName("merchant/edit_helibao_channel");
        return view;
    }

    @RequestMapping(value = "edit_fuyou_channel")
    public ModelAndView edit_fuyou_channel(ModelAndView view, String merchantAlias) {
        view.addObject("merchantAlias", merchantAlias);
        view.setViewName("merchant/edit_fuyou_channel");
        return view;
    }

    @RequestMapping(value = "edit_huiju_channel")
    public ModelAndView edit_huiju_channel(ModelAndView view, String merchantAlias) {
        view.addObject("merchantAlias", merchantAlias);
        view.setViewName("merchant/edit_huiju_channel");
        return view;
    }

    @RequestMapping(value = "edit_yeepay_channel")
    public ModelAndView edit_yeepay_channel(ModelAndView view, String merchantAlias) {
        view.addObject("merchantAlias", merchantAlias);
        view.setViewName("merchant/edit_yeepay_channel");
        return view;
    }

    @RequestMapping(value = "edit_merchant_channel_ajax")
    public ResultMessage edit_merchant_channel_ajax(Merchant merchant, String merchantChannels) {
        Merchant record = merchantService.selectByPrimaryKey(merchant.getMerchantAlias());
        if (record == null) {
            return new ResultMessage(ResponseEnum.M4000.getCode(), "商户不存在");
        }
        record.setMerchantChannel(merchantChannels);
        record.setHlb_id(merchant.getHlb_id());
        record.setHlb_rsa_private_key(merchant.getHlb_rsa_private_key());
        record.setFuyou_merid(merchant.getFuyou_merid());
        record.setFuyou_secureid(merchant.getFuyou_secureid());
        record.setFuyou_h5key(merchant.getFuyou_h5key());
        record.setHuiju_id(merchant.getHuiju_id());
        record.setHuiju_md5_key(merchant.getHuiju_md5_key());
        record.setYeepay_appkey(merchant.getYeepay_appkey());
        merchantService.updateByPrimaryKeySelective(record);
        return new ResultMessage(ResponseEnum.M2000);
    }

    @RequestMapping(value = "find_merchant_channel_ajax")
    public ResultMessage find_merchant_channel_ajax(String merchantAlias) {
        Merchant merchant = merchantService.selectByPrimaryKey(merchantAlias);
        if (merchant == null) {
            return new ResultMessage(ResponseEnum.M4000.getCode(), "商户不存在");
        }
        return new ResultMessage(ResponseEnum.M2000, merchant);
    }

}
