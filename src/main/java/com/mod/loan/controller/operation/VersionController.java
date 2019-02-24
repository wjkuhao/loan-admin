package com.mod.loan.controller.operation;

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
import com.mod.loan.model.Version;
import com.mod.loan.service.VersionService;

@RestController
@RequestMapping(value = "operation")
public class VersionController {

	public static final Logger logger = LoggerFactory.getLogger(VersionController.class);

	@Autowired
	private VersionService versionService;

	@RequestMapping(value = "version_list")
	public ModelAndView version_list(ModelAndView view, String merchantAlias) {
		view.addObject("merchantAlias", merchantAlias);
		view.setViewName("operation/version_list");
		return view;
	}

	@RequestMapping(value = "version_list_ajax", method = { RequestMethod.POST })
	public ResultMessage version_list_ajax(Version version, Page page) {
		return new ResultMessage(ResponseEnum.M2000, versionService.findVersionList(version, page), page);
	}

	@RequestMapping(value = "version_edit")
	public ModelAndView version_edit(ModelAndView view, Long id, String versionAlias) {
		view.addObject("versionAlias", versionAlias);
		if (id == null) {
			view.setViewName("operation/version_add");
		} else {
			view.addObject("id", id);
			view.setViewName("operation/version_edit");
		}
		return view;
	}

	@RequestMapping(value = "version_edit_ajax", method = { RequestMethod.POST })
	public ResultMessage version_edit_ajax(Version version) {
		if (StringUtils.isBlank(version.getVersionAlias())) {
			return new ResultMessage(ResponseEnum.M4000.getCode(), "APP别名不能为空");
		}
		if (StringUtils.isBlank(version.getVersionName())) {
			return new ResultMessage(ResponseEnum.M4000.getCode(), "版本名称不能为空");
		}
		if (version.getVersionCode() == null) {
			return new ResultMessage(ResponseEnum.M4000.getCode(), "版本编号不能为空");
		}
		if (StringUtils.isBlank(version.getVersionUrl())) {
			return new ResultMessage(ResponseEnum.M4000.getCode(), "下载地址不能为空");
		}
		if (version.getId() == null) {
			versionService.insertSelective(version);
		} else {
			versionService.updateByPrimaryKeySelective(version);
		}
		return new ResultMessage(ResponseEnum.M2000);
	}

	@RequestMapping(value = "version_detail_ajax", method = { RequestMethod.POST })
	public ResultMessage version_detail_ajax(Long id) {
		return new ResultMessage(ResponseEnum.M2000, versionService.selectByPrimaryKey(id));
	}

}
