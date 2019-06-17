package com.mod.loan.controller.market;

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
import com.mod.loan.mapper.MarketProductMapper;
import com.mod.loan.model.MarketProduct;
import com.mod.loan.service.MarketService;

@RestController
@RequestMapping(value = "market")
public class ProductController {
	public static final Logger logger = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	MarketService marketService;
	@Autowired
	MarketProductMapper productMapper;

	@RequestMapping(value = "product_list")
	public ModelAndView product_list(ModelAndView view) {
		view.setViewName("market/product_list");
		return view;
	}

	@RequestMapping(value = "product_list_ajax", method = { RequestMethod.POST })
	public ResultMessage product_list_ajax(MarketProduct marketProduct, Page page) {
		return new ResultMessage(ResponseEnum.M2000, marketService.findProductList(marketProduct, page), page);
	}

	@RequestMapping(value = "product_edit")
	public ModelAndView product_edit(ModelAndView view, Long id) {
			if (id == null) {
				view.setViewName("market/product_add");
			} else {
				view.setViewName("market/product_edit");
				view.addObject("id", id);
			}
			return view;
	}

	@RequestMapping(value = "product_edit_ajax", method = { RequestMethod.POST })
	public ResultMessage product_edit_ajax(MarketProduct marketProduct) {
		if (StringUtils.isBlank(marketProduct.getProductName())) {
			return new ResultMessage(ResponseEnum.M4000.getCode(), "名称不能为空");
		}
		if (StringUtils.isBlank(marketProduct.getProductSlogan())) {
			return new ResultMessage(ResponseEnum.M4000.getCode(), "标语不能为空");
		}
		if (StringUtils.isBlank(marketProduct.getProductUrl())) {
			return new ResultMessage(ResponseEnum.M4000.getCode(), "链接不能为空");
		}
		if (marketProduct.getModuleId() == null) {
			return new ResultMessage(ResponseEnum.M4000.getCode(), "请选择类目");
		}
		if (marketProduct.getId() == null) {
			productMapper.insertSelective(marketProduct);
		} else {
			productMapper.updateByPrimaryKeySelective(marketProduct);
		}
		return new ResultMessage(ResponseEnum.M2000);
	}

	@RequestMapping(value = "product_detail_ajax", method = { RequestMethod.POST })
	public ResultMessage product_detail_ajax(Long id) {
		return new ResultMessage(ResponseEnum.M2000, productMapper.selectByPrimaryKey(id));
	}
}
