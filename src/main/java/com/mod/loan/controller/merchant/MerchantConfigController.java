package com.mod.loan.controller.merchant;

import com.mod.loan.common.enums.ResponseEnum;
import com.mod.loan.common.model.ResultMessage;
import com.mod.loan.model.MerchantConfig;
import com.mod.loan.service.MerchantConfigService;
import com.mod.loan.util.TimeUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value = "/merchant_config")
public class MerchantConfigController {

    private static final Logger logger = LoggerFactory.getLogger(MerchantConfigController.class);

    private final MerchantConfigService merchantConfigService;

    @Autowired
    public MerchantConfigController(MerchantConfigService merchantConfigService) {
        this.merchantConfigService = merchantConfigService;
    }


    @GetMapping("/edit_mxrisk_channel")
    public ModelAndView editMxriskChannel(ModelAndView view, String merchantAlias) {
        view.addObject("merchantAlias", merchantAlias);
        view.setViewName("merchant/edit_mxrisk_channel");
        return view;
    }

    @GetMapping("/find_merchant_config")
    public ResultMessage findMerchantConfig(String merchantAlias) {
        MerchantConfig _merchantConfig = new MerchantConfig();
        _merchantConfig.setMerchant(merchantAlias);
        MerchantConfig merchantConfig = merchantConfigService.selectOne(_merchantConfig);
        return new ResultMessage(ResponseEnum.M2000, merchantConfig);
    }

    @PostMapping("/edit_merchant_mx_risk_token")
    public ResultMessage updateMerchantMxRiskToken(@RequestParam String merchantAlias,
                                                   @RequestParam(defaultValue = "") String mxRiskToken,
                                                   @RequestParam(defaultValue = "") String mxRiskRenewToken) {
        if (StringUtils.isBlank(merchantAlias) || StringUtils.isBlank(mxRiskToken)) {
            return new ResultMessage(ResponseEnum.M4000.getCode(), "商户或者风控token为空");
        }
        MerchantConfig merchantConfig = new MerchantConfig();
        merchantConfig.setMerchant(merchantAlias);
        MerchantConfig _merchantConfig = merchantConfigService.selectOne(merchantConfig);
        merchantConfig.setMxRiskToken(mxRiskToken);
        merchantConfig.setMxRiskRenewToken(mxRiskRenewToken);
        int upsertCount;
        if (null == _merchantConfig) {
            // 新增
            merchantConfig.setCreateTime(TimeUtils.getTime());
            upsertCount = merchantConfigService.insertSelective(merchantConfig);
        } else {
            // 更新
            merchantConfig.setId(_merchantConfig.getId());
            merchantConfig.setUpdateTime(TimeUtils.getTime());
            upsertCount = merchantConfigService.updateByPrimaryKeySelective(merchantConfig);
        }
        if (upsertCount < 1) {
            logger.error("写入或更新商户风控token失败");
            return new ResultMessage(ResponseEnum.M4000.getCode(), "写入或更新商户风控token失败");
        }
        return new ResultMessage(ResponseEnum.M2000, merchantConfig);
    }

}
