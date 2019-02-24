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
import com.mod.loan.common.model.ResultMessage;
import com.mod.loan.mapper.MarketConfigMapper;
import com.mod.loan.model.MarketConfig;

@RestController
@RequestMapping(value = "market")
public class ConfigController {
	public static final Logger logger = LoggerFactory.getLogger(ConfigController.class);

	@Autowired
	MarketConfigMapper configMapper;

	@RequestMapping(value = "config_list")
	public ModelAndView config_list(ModelAndView view) {
		view.setViewName("market/config_list");
		return view;
	}

	@RequestMapping(value = "config_list_ajax", method = { RequestMethod.POST })
	public ResultMessage config_list_ajax() {
		return new ResultMessage(ResponseEnum.M2000, configMapper.selectAll());
	}

	@RequestMapping(value = "config_edit")
	public ModelAndView product_edit(ModelAndView view, Long id) {
		if (id == null) {
			view.setViewName("market/config_add");
		} else {
			view.setViewName("market/config_edit");
			view.addObject("id", id);
		}
		return view;
	}

	@RequestMapping(value = "config_edit_ajax", method = { RequestMethod.POST })
	public ResultMessage config_edit_ajax(MarketConfig marketConfig) {
		if (StringUtils.isBlank(marketConfig.getName())) {
			return new ResultMessage(ResponseEnum.M4000.getCode(), "名称不能为空");
		}
		if (StringUtils.isBlank(marketConfig.getCode())) {
			return new ResultMessage(ResponseEnum.M4000.getCode(), "标语不能为空");
		}
		if (marketConfig.getId() == null) {
			configMapper.insertSelective(marketConfig);
		} else {
			configMapper.updateByPrimaryKeySelective(marketConfig);
		}
		return new ResultMessage(ResponseEnum.M2000);
	}

	@RequestMapping(value = "config_detail_ajax", method = { RequestMethod.POST })
	public ResultMessage config_detail_ajax(Long id) {
		return new ResultMessage(ResponseEnum.M2000, configMapper.selectByPrimaryKey(id));
	}
}
