package com.mod.loan.controller.system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.mod.loan.common.enums.ResponseEnum;
import com.mod.loan.common.model.ResultMessage;
import com.mod.loan.model.MerchantResource;
import com.mod.loan.service.MerchantResourceService;

@RestController
@RequestMapping(value = "system")
public class MerchantResourceController {
	public static final Logger logger = LoggerFactory.getLogger(ResourceController.class);

	@Autowired
	private MerchantResourceService merchantResourceService;

	@RequestMapping(value = "merchant_resource_list")
	public ModelAndView merchant_resource_list(ModelAndView view) {
		view.setViewName("system/merchant_resource_list");
		return view;
	}

	/**
	 * 获取权限菜单
	 * 
	 * @param parentId
	 * @return
	 */
	@RequestMapping(value = "merchant_resource_list_ajax", method = { RequestMethod.POST })
	public ResultMessage merchant_resource_list_ajax(Long parentId) {
		return new ResultMessage(ResponseEnum.M2000, merchantResourceService.findMerchantResourceList(parentId, null));
	}

	@RequestMapping(value = "merchant_resource_add")
	public ModelAndView merchant_resource_add(ModelAndView view) {
		view.setViewName("system/merchant_resource_add");
		return view;
	}

	@RequestMapping(value = "merchant_resource_add_ajax", method = { RequestMethod.POST })
	public ResultMessage merchant_resource_add_ajax(MerchantResource merchantResource) {
		try {
			merchantResource.setResourceStatus(0);
			merchantResourceService.insertSelective(merchantResource);
			return new ResultMessage(ResponseEnum.M2000);
		} catch (Exception e) {
			logger.error("菜单新增失败,error_msg={}", e.getMessage());
		}
		return new ResultMessage(ResponseEnum.M4000);
	}

	@RequestMapping(value = "merchant_resource_update")
	public ModelAndView merchant_resource_update(ModelAndView view, Long id) {
		view.addObject("id", id);
		view.setViewName("system/merchant_resource_edit");
		return view;
	}

	@RequestMapping(value = "merchant_resource_update_ajax", method = { RequestMethod.POST })
	public ResultMessage merchant_resource_update_ajax(MerchantResource merchantResource) {
		try {
			merchantResourceService.updateByPrimaryKeySelective(merchantResource);
			return new ResultMessage(ResponseEnum.M2000);
		} catch (Exception e) {
			logger.error("菜单修改失败,error_msg={}", e.getMessage());
		}
		return new ResultMessage(ResponseEnum.M4000);
	}

	@RequestMapping(value = "merchant_resource_detail_ajax")
	public ResultMessage merchant_resource_detail_ajax(Long id) {
		MerchantResource merchantResource = merchantResourceService.selectByPrimaryKey(id);
		return new ResultMessage(ResponseEnum.M2000, merchantResource);
	}
}
