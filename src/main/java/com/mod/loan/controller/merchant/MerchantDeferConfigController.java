package com.mod.loan.controller.merchant;

import com.mod.loan.common.enums.ResponseEnum;
import com.mod.loan.common.model.ResultMessage;
import com.mod.loan.model.Merchant;
import com.mod.loan.model.MerchantDeferConfig;
import com.mod.loan.service.MerchantDeferConfigService;
import com.mod.loan.service.MerchantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 续期配置接口
 *
 * @author kibear
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/merchant")
public class MerchantDeferConfigController {

    public static final Logger logger = LoggerFactory.getLogger(MerchantDeferConfigController.class);

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private MerchantDeferConfigService merchantDeferConfigService;

    /**
     * 续期配置
     */
    @RequestMapping(value = "merchant_defer_edit")
    public ModelAndView merchant_defer_edit(ModelAndView view, String merchant) {
        view.addObject("merchant", merchant);
        //查询该商户下是否配置续期
        MerchantDeferConfig deferConfig = new MerchantDeferConfig();
        deferConfig.setMerchant(merchant);
        deferConfig = merchantDeferConfigService.selectOne(deferConfig);
        if (deferConfig == null || deferConfig.getId() == null) {
            view.setViewName("merchant/merchant_defer_add");
        } else {
            view.addObject("id", deferConfig.getId());
            view.setViewName("merchant/merchant_defer_edit");
        }
        return view;
    }

    /**
     * 续期配置保存
     */
    @RequestMapping(value = "merchant_defer_edit_ajax")
    public ResultMessage edit_merchant_defer_ajax(MerchantDeferConfig deferConfig) {
        Merchant record = merchantService.selectByPrimaryKey(deferConfig.getMerchant());
        if (record == null) {
            return new ResultMessage(ResponseEnum.M4000.getCode(), "商户不存在");
        }
        if (deferConfig.getDailyDeferFee() == null || deferConfig.getDailyDeferFee() == 0) {
            return new ResultMessage(ResponseEnum.M4000.getCode(), "日续期费不能为空");
        }
        if (deferConfig.getDailyDeferRate() == null || deferConfig.getDailyDeferRate() == 0) {
            return new ResultMessage(ResponseEnum.M4000.getCode(), "日续期费率不能为空");
        }
        if (deferConfig.getId() != null) {
            merchantDeferConfigService.updateByPrimaryKey(deferConfig);
        } else {
            merchantDeferConfigService.insert(deferConfig);
        }
        return new ResultMessage(ResponseEnum.M2000);
    }

    /**
     * 查询续期配置详情
     */
    @RequestMapping(value = "merchant_defer_detail_ajax", method = {RequestMethod.POST})
    public ResultMessage merchant_defer_detail_ajax(int id) {
        return new ResultMessage(ResponseEnum.M2000, merchantDeferConfigService.selectByPrimaryKey(id));
    }
}
