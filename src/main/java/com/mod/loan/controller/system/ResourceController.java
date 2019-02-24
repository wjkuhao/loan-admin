package com.mod.loan.controller.system;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.mod.loan.common.enums.ResponseEnum;
import com.mod.loan.common.model.ResultMessage;
import com.mod.loan.model.Resource;
import com.mod.loan.service.ResourceService;

@RestController
@RequestMapping(value = "system")
public class ResourceController {
	public static final Logger logger = LoggerFactory.getLogger(ResourceController.class);

	@Autowired
	private ResourceService resourceService;

	@RequestMapping(value = "resource_list")
	public ModelAndView resource_list(ModelAndView view) {
		view.setViewName("system/resource_list");
		return view;
	}

	/**
	 * 获取权限菜单
	 * 
	 * @param parentId
	 * @return
	 */
	@RequestMapping(value = "resource_list_ajax", method = { RequestMethod.POST })
	public ResultMessage resource_list_ajax(Long parentId) {
		List<Resource> parentResourceList = resourceService.findResourceList(parentId, null);
		return new ResultMessage(ResponseEnum.M2000, parentResourceList);
	}

	@RequestMapping(value = "resource_add")
	public ModelAndView resource_add(ModelAndView view) {
		view.setViewName("system/resource_add");
		return view;
	}

	@RequestMapping(value = "resource_add_ajax", method = { RequestMethod.POST })
	public ResultMessage resource_add_ajax(Resource resource) {
		try {
			resource.setResourceStatus(0);
			resourceService.insertSelective(resource);
			return new ResultMessage(ResponseEnum.M2000);
		} catch (Exception e) {
			logger.error("菜单新增失败,error_msg={}", e.getMessage());
		}
		return new ResultMessage(ResponseEnum.M4000);
	}

	@RequestMapping(value = "resource_update")
	public ModelAndView resource_update(ModelAndView view, Long id) {
		view.addObject("id", id);
		view.setViewName("system/resource_edit");
		return view;
	}

	@RequestMapping(value = "resource_update_ajax", method = { RequestMethod.POST })
	public ResultMessage resource_update_ajax(HttpServletRequest request, Resource resource) {
		try {
			resourceService.updateByPrimaryKeySelective(resource);
			return new ResultMessage(ResponseEnum.M2000);
		} catch (Exception e) {
			logger.error("菜单修改失败,error_msg={}", e.getMessage());
		}
		return new ResultMessage(ResponseEnum.M4000);
	}

	@RequestMapping(value = "resource_detail_ajax")
	public ResultMessage resource_detail_ajax(Long id) {
		Resource resource = resourceService.selectByPrimaryKey(id);
		return new ResultMessage(ResponseEnum.M2000, resource);
	}
}
