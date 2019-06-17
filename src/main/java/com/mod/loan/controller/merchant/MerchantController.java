package com.mod.loan.controller.merchant;

import com.mod.loan.common.enums.ResponseEnum;
import com.mod.loan.common.model.Page;
import com.mod.loan.common.model.RequestThread;
import com.mod.loan.common.model.ResultMessage;
import com.mod.loan.model.Merchant;
import com.mod.loan.model.MerchantQuotaConfig;
import com.mod.loan.service.MerchantQuotaConfigService;
import com.mod.loan.service.MerchantService;
import com.mod.loan.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "merchant")
public class MerchantController {

    public static final Logger logger = LoggerFactory.getLogger(MerchantController.class);

    @Autowired
    private MerchantService merchantService;


    @Autowired
    private MerchantQuotaConfigService merchantQuotaConfigService;

    @RequestMapping(value = "merchant_list")
    public ModelAndView merchant_list(ModelAndView view) {
        view.setViewName("merchant/merchant_list");
        return view;
    }

    @RequestMapping(value = "merchant_quota")
    public ModelAndView merchant_quota(ModelAndView view) {
        view.setViewName("merchant/merchant_quota");
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
        if (merchant.getBindType() != 1 && merchant.getBindType() != 2 && merchant.getBindType() != 3
                && merchant.getBindType() != 4 && merchant.getBindType() != 5 && merchant.getBindType() != 6) {
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

    @RequestMapping(value = "edit_changjie_channel")
    public ModelAndView edit_changjie_channel(ModelAndView view, String merchantAlias) {
        view.addObject("merchantAlias", merchantAlias);
        view.setViewName("merchant/edit_changjie_channel");
        return view;
    }

    @RequestMapping(value = "edit_kuaiqian_channel")
    public ModelAndView edit_kuaiqian_channel(ModelAndView view, String merchantAlias) {
        view.addObject("merchantAlias", merchantAlias);
        view.setViewName("merchant/edit_kuaiqian_channel");
        return view;
    }

    @RequestMapping(value = "edit_huichao_channel")
    public ModelAndView edit_huichao_channel(ModelAndView view, String merchantAlias) {
        view.addObject("merchantAlias", merchantAlias);
        view.setViewName("merchant/edit_huichao_channel");
        return view;
    }

    @RequestMapping(value = "edit_merchant_channel_ajax")
    public ResultMessage edit_merchant_channel_ajax(Merchant merchant, String merchantChannels) {
        Merchant record = merchantService.selectByPrimaryKey(merchant.getMerchantAlias());
        if (record == null) {
            return new ResultMessage(ResponseEnum.M4000.getCode(), "商户不存在");
        }
        try {
            record.setMerchantChannel(merchantChannels);
            record.setHlb_id(merchant.getHlb_id());
            record.setHlb_rsa_private_key(merchant.getHlb_rsa_private_key());
            record.setFuyou_merid(merchant.getFuyou_merid());
            record.setFuyou_secureid(merchant.getFuyou_secureid());
            record.setFuyou_h5key(merchant.getFuyou_h5key());
            record.setHuiju_id(merchant.getHuiju_id());
            record.setHuiju_md5_key(merchant.getHuiju_md5_key());

            record.setYeepay_repay_appkey(merchantService.encodeKey(record.getYeepay_repay_appkey(), merchant.getYeepay_repay_appkey()));
            record.setYeepay_repay_private_key(merchantService.encodeKey(record.getYeepay_repay_appkey(), merchant.getYeepay_repay_private_key()));
            record.setYeepay_loan_appkey(merchantService.encodeKey(record.getYeepay_loan_appkey(), merchant.getYeepay_loan_appkey()));
            record.setYeepay_loan_private_key(merchantService.encodeKey(record.getYeepay_loan_private_key(), merchant.getYeepay_loan_private_key()));
            record.setYeepay_group_no(merchantService.encodeKey(record.getYeepay_group_no(), merchant.getYeepay_group_no()));

            record.setHlbMerchantSign(merchant.getHlbMerchantSign());
            record.setKqCertPath(merchant.getKqCertPath());
            record.setKqCertPwd(merchant.getKqCertPwd());
            record.setKqMerchantCode(merchant.getKqMerchantCode());
            record.setKqMerchantId(merchant.getKqMerchantId());
            record.setKqTerminalId(merchant.getKqTerminalId());

            record.setCjPartnerId(merchant.getCjPartnerId());
            record.setCjPublicKey(merchant.getCjPublicKey());
            record.setCjMerchantPrivateKey(merchant.getCjMerchantPrivateKey());

            record.setHuichaoMerid(merchant.getHuichaoMerid());
            record.setHuichaoPublicKey(merchant.getHuichaoPublicKey());
            record.setHuichaoMerchantPayPrivateKey(merchant.getHuichaoMerchantPayPrivateKey());
            record.setHuichaoMerchantRepayPrivateKey(merchant.getHuichaoMerchantRepayPrivateKey());
            merchantService.updateByPrimaryKeySelective(record);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultMessage(ResponseEnum.M4000.getCode(), "encode error");
        }

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

    /**
     * 商户提额配置
     * @param page
     * @return
     */
    @RequestMapping(value = "merchant_quota_ajax")
    public ResultMessage merchant_quota_ajax(MerchantQuotaConfig merchantQuotaConfig,Page page) {
        Map<String, Object> param = new HashMap<>();
        if (merchantQuotaConfig.getStatus()!=null){
            param.put("status", merchantQuotaConfig.getStatus());
        }else{
            param.put("status", null);
        }
        param.put("merchant",  StringUtils.isNotEmpty(merchantQuotaConfig.getMerchant())? merchantQuotaConfig.getMerchant():null);
        param.put("quotaName",  StringUtils.isNotEmpty(merchantQuotaConfig.getQuotaName())? merchantQuotaConfig.getQuotaName():null);
        return new ResultMessage(ResponseEnum.M2000, merchantQuotaConfigService.findMerchantQuotaConfigList(param, page), page);
    }

    @RequestMapping(value = "quota_get")
    public ModelAndView quota_get(ModelAndView mv, MerchantQuotaConfig merchantQuotaConfig) {
        if (merchantQuotaConfig.getId() == null) {
            mv.setViewName("merchant/quota_add");
        } else {
            merchantQuotaConfig = merchantQuotaConfigService.selectOne(merchantQuotaConfig);
            mv.addObject("QuotaConfig", merchantQuotaConfig);
            mv.setViewName("merchant/quota_edit");
        }
        return mv;
    }

    /**
     * 商户提额配置增加/修改
     * @param
     * @return
     */
    @RequestMapping(value = "quota_edit_ajax")
    public ResultMessage quota_add_ajax(MerchantQuotaConfig merchantQuotaConfig) {
        if (StringUtils.isBlank(merchantQuotaConfig.getQuotaName())) {
            return new ResultMessage(ResponseEnum.M4000.getCode(), "额度名称不能为空");
        }
        if (merchantQuotaConfig.getQuotaValue()==null) {
            return new ResultMessage(ResponseEnum.M4000.getCode(), "提升额度不能为空");
        }
        if (StringUtils.isBlank(merchantQuotaConfig.getPresetValue())) {
            return new ResultMessage(ResponseEnum.M4000.getCode(), "预设值不能为空");
        }
        if (StringUtils.isBlank(merchantQuotaConfig.getMerchant())) {
            return new ResultMessage(ResponseEnum.M4000.getCode(), "商户别名不能为空");
        }
        if ("天机分".equals(merchantQuotaConfig.getQuotaName())){
            merchantQuotaConfig.setQuotaType(1);
        }else if ("展期次数".equals(merchantQuotaConfig.getQuotaName())){
            merchantQuotaConfig.setQuotaType(2);
        }
        if (merchantQuotaConfig.getId() != null) {
            merchantQuotaConfig.setUpdateTime(new Date());
            merchantQuotaConfigService.updateByPrimaryKeySelective(merchantQuotaConfig);
        } else {
            merchantQuotaConfig.setCreateTime(new Date());
            merchantQuotaConfig.setUpdateTime(new Date());
            merchantQuotaConfigService.insertSelective(merchantQuotaConfig);
        }
        return new ResultMessage(ResponseEnum.M2000);
    }


    /**
     * 商户提额配置删除
     * @param
     * @return
     */
    @RequestMapping(value = "quota_del_ajax")
    public ResultMessage quota_del_ajax(Long id) {
        MerchantQuotaConfig merchantQuotaConfig = merchantQuotaConfigService.selectByPrimaryKey(id);
        if (merchantQuotaConfig==null){
            return new ResultMessage(ResponseEnum.M4000.getCode(), "非法操作");
        }
        merchantQuotaConfigService.deleteByPrimaryKey(id);
        return new ResultMessage(ResponseEnum.M2000);
    }


}
