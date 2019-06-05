package com.mod.loan.controller.merchant;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.mod.loan.common.enums.ResponseEnum;
import com.mod.loan.common.model.Page;
import com.mod.loan.common.model.ResultMessage;
import com.mod.loan.model.MerchantRate;
import com.mod.loan.service.MerchantRateService;

@RestController
@RequestMapping(value = "merchant")
public class MerchantRateController {

	public static final Logger logger = LoggerFactory.getLogger(MerchantRateController.class);

	@Autowired
	private MerchantRateService merchantRateService;

	@RequestMapping(value = "merchant_rate_list")
	public ModelAndView merchant_rate_list(ModelAndView view, String merchantAlias) {
		view.addObject("merchant", merchantAlias);
		view.setViewName("merchant/merchant_rate_list");
		return view;
	}

	@RequestMapping(value = "merchant_rate_list_ajax", method = { RequestMethod.POST })
	public ResultMessage merchant_rate_list_ajax(MerchantRate merchantRate, Page page) {
		return new ResultMessage(ResponseEnum.M2000, merchantRateService.findMerchantRateList(merchantRate, page),
				page);
	}

	@RequestMapping(value = "merchant_rate_edit")
	public ModelAndView merchant_rate_edit(ModelAndView view, Long id, String merchant) {
		view.addObject("merchant", merchant);
		if (id == null) {
			view.setViewName("merchant/merchant_rate_add");
		} else {
			view.addObject("id", id);
			view.setViewName("merchant/merchant_rate_edit");
		}
		return view;
	}

	@RequestMapping(value = "merchant_rate_edit_ajax", method = { RequestMethod.POST })
	public ResultMessage merchant_rate_edit_ajax(MerchantRate merchantRate) {
		if (StringUtils.isBlank(merchantRate.getProductName())) {
			return new ResultMessage(ResponseEnum.M4000.getCode(), "产品名称不能为空");
		}
		if (merchantRate.getProductDay() == null || merchantRate.getProductDay() < 0) {
			return new ResultMessage(ResponseEnum.M4000.getCode(), "请重新输入产品期限");
		}
		if (merchantRate.getProductMoney() == null
				|| new BigDecimal(5000).compareTo(merchantRate.getProductMoney()) < 0) {
			return new ResultMessage(ResponseEnum.M4000.getCode(), "请重新输入借款金额");
		}
		if (merchantRate.getProductLevel() == null) {
			return new ResultMessage(ResponseEnum.M4000.getCode(), "优先级不能为空");
		}
		if (merchantRate.getTotalRate() == null || new BigDecimal(30).compareTo(merchantRate.getTotalRate()) < 0) {
			return new ResultMessage(ResponseEnum.M4000.getCode(), "请重新输入综合费率");
		}
		if (merchantRate.getOverdueRate() == null || new BigDecimal(6).compareTo(merchantRate.getOverdueRate()) < 0) {
			return new ResultMessage(ResponseEnum.M4000.getCode(), "请重新输入逾期费率");
		}
		if (merchantRate.getBorrowType() == null
				|| (merchantRate.getBorrowType() > 4 && merchantRate.getBorrowType() != 99)) {
			return new ResultMessage(ResponseEnum.M4000.getCode(), "请重新输入借款次数");
		}
		if (StringUtils.isBlank(merchantRate.getMerchant())) {
			return new ResultMessage(ResponseEnum.M4000.getCode(), "请重新修改");
		}
		if (merchantRate.getId() == null) {
			merchantRateService.insertSelective(merchantRate);
		} else if (!merchantRateService.selectByPrimaryKey(merchantRate.getId()).getMerchant().equals(merchantRate.getMerchant())) {
			return new ResultMessage(ResponseEnum.M4000.getCode(), "请重新修改");
		} else {
			merchantRateService.updateByPrimaryKeySelective(merchantRate);
		}
		return new ResultMessage(ResponseEnum.M2000);
	}

	@RequestMapping(value = "merchant_rate_detail_ajax", method = { RequestMethod.POST })
	public ResultMessage merchant_rate_detail_ajax(Long id) {
		return new ResultMessage(ResponseEnum.M2000, merchantRateService.selectByPrimaryKey(id));
	}

}
