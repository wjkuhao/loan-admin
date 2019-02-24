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
import com.mod.loan.mapper.MarketModuleMapper;
import com.mod.loan.model.MarketModule;
import com.mod.loan.service.MarketService;

@RestController
@RequestMapping(value = "market")
public class ModuleController {
	public static final Logger logger = LoggerFactory.getLogger(ModuleController.class);

	@Autowired
	MarketService marketService;
	@Autowired
	MarketModuleMapper moduleMapper;

	@RequestMapping(value = "module_list")
	public ModelAndView module_list(ModelAndView view) {
		view.setViewName("market/module_list");
		return view;
	}

	@RequestMapping(value = "module_list_ajax")
	public ResultMessage module_list_ajax(MarketModule marketModule, Page page) {
		return new ResultMessage(ResponseEnum.M2000, marketService.findModuleList(marketModule, page), page);
	}

	@RequestMapping(value = "module_edit")
	public ModelAndView module_edit(ModelAndView view, Long id) {
		if (id == null) {
			view.setViewName("market/module_add");
		} else {
			view.setViewName("market/module_edit");
			view.addObject("id", id);
		}
		return view;
	}

	@RequestMapping(value = "module_edit_ajax", method = RequestMethod.POST)
	public ResultMessage module_edit_ajax(MarketModule marketModule) {
		if (StringUtils.isBlank(marketModule.getModuleName())) {
			return new ResultMessage(ResponseEnum.M4000.getCode(), "名字不能为空");
		}
		if (marketModule.getChannelId()==null) {
			return new ResultMessage(ResponseEnum.M4000.getCode(), "请选择栏目");
		}
		if (marketModule.getId() == null) {
			moduleMapper.insertSelective(marketModule);
		} else {
			moduleMapper.updateByPrimaryKeySelective(marketModule);
		}
		return new ResultMessage(ResponseEnum.M2000);
	}

	@RequestMapping(value = "module_detail_ajax")
	public ResultMessage module_detail_ajax(Long id) {
		return new ResultMessage(ResponseEnum.M2000, moduleMapper.selectByPrimaryKey(id));
	}

	@RequestMapping(value = "module_able_list")
	public ResultMessage module_able_list() {
		MarketModule record = new MarketModule();
//		record.setModuleStatus(1);
		return new ResultMessage(ResponseEnum.M2000, moduleMapper.select(record));
	}
}
