package com.mod.loan.controller.merchant;

import com.mod.loan.common.enums.ResponseEnum;
import com.mod.loan.common.model.Page;
import com.mod.loan.common.model.ResultMessage;
import com.mod.loan.model.MerchantConfig;
import com.mod.loan.service.MerchantConfigService;
import com.mod.loan.util.StringUtil;
import com.mod.loan.util.TimeUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/merchant_config")
public class MerchantConfigController {

    private static final Logger logger = LoggerFactory.getLogger(MerchantConfigController.class);

    private final MerchantConfigService merchantConfigService;

    @Autowired
    public MerchantConfigController(MerchantConfigService merchantConfigService) {
        this.merchantConfigService = merchantConfigService;
    }

    @RequestMapping("/merchant_config_list")
    public ModelAndView merchant_list(ModelAndView view) {
        view.setViewName("merchant/merchant_config_list");

        return view;
    }
    // 查找全部商户配置
    @RequestMapping(value = "/merchant_config_list_ajax", method = { RequestMethod.POST })
    public ResultMessage merchant_congfig_list_ajax(MerchantConfig merchantConfig,Page page){
        return new ResultMessage(ResponseEnum.M2000, merchantConfigService.findMerchantConfigList(merchantConfig,page),page);
    }

    @RequestMapping(value = "/merchant_config_edit")
    public ModelAndView merchant_config_edit(ModelAndView view, Long id) {
        if (id == null) {
            view.setViewName("merchant/merchant_config_add");
        } else {
            view.setViewName("merchant/merchant_config_edit");
            view.addObject("id", id);
        }
        return view;
    }

    // 新增一个
    @RequestMapping(value = "/merchant_config_add_ajax",method = {RequestMethod.POST})
    public ResultMessage merchant_config_add(MerchantConfig merchantConfig){
        if(StringUtils.isBlank(merchantConfig.getMxRiskToken())){
            return new ResultMessage(ResponseEnum.M4000.getCode(),"风控默认token不能为空");
        }
        if(StringUtils.isBlank(merchantConfig.getMxRiskRenewToken())){
            return new ResultMessage(ResponseEnum.M4000.getCode(),"风控续借token不能为空");
        }
        if(StringUtils.isBlank(merchantConfig.getH5Url())){
            return new ResultMessage(ResponseEnum.M4000.getCode(),"h5地址不能为空");
        }
        if(merchantConfig.getOverdueBlacklistDay()==null ){
            return new ResultMessage(ResponseEnum.M4000.getCode(),"加入黑名单逾期天数不能为空");
        }
        if(StringUtils.isBlank(merchantConfig.getRejectKeyword())){
            return new ResultMessage(ResponseEnum.M4000.getCode(),"关键字不能为空");
        }
        if(StringUtils.isBlank(merchantConfig.getServicePhone())){
            return new ResultMessage(ResponseEnum.M4000.getCode(),"客服电话不能为空");
        }
        if(merchantConfig.getMaxOverdueFeeRate()==null){
            return new ResultMessage(ResponseEnum.M4000.getCode(),"最大逾期费费率不能为空");
        }else {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println(merchantConfig.getId());
            if(merchantConfig.getId()==null){
                merchantConfig.setCreateTime(sdf.format(date));
                merchantConfig.setUpdateTime(sdf.format(date));
                merchantConfigService.insert(merchantConfig);
                return new ResultMessage(ResponseEnum.M2000);
            }else {
                merchantConfig.setUpdateTime(sdf.format(date));
                merchantConfigService.updateByPrimaryKey(merchantConfig);
            }

        }
        return new ResultMessage(ResponseEnum.M2000);
    }
    // 通过ID进行查找
    @RequestMapping("/find_merchant_config_byId")
    public ResultMessage findMerchantConfigById(ModelAndView view,Integer id){
        return new ResultMessage(ResponseEnum.M2000,merchantConfigService.selectByPrimaryKey(id));
    }


    // 通过ID进行删除
    @RequestMapping("/del_merchant_config_byId")
    public ResultMessage delMerchantConfigById(Integer id){
        return new ResultMessage(ResponseEnum.M2000,merchantConfigService.deleteByPrimaryKey(id));
    }

    @GetMapping("/edit_mxrisk_channel")
    public ModelAndView editMxriskChannel(ModelAndView view, String merchantAlias) {
        view.addObject("merchantAlias", merchantAlias);
        view.setViewName("merchant/edit_mxrisk_channel");
        return view;
    }

    @GetMapping("/find_merchnat_config")
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
