package com.mod.loan.controller.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.mod.loan.common.enums.ResponseEnum;
import com.mod.loan.common.model.Page;
import com.mod.loan.common.model.ResultMessage;
import com.mod.loan.model.Resource;
import com.mod.loan.model.Role;
import com.mod.loan.service.ResourceService;
import com.mod.loan.service.RoleService;
import com.mod.loan.util.ArrayUtil;
import com.mod.loan.util.ParamHelper;
import com.mod.loan.util.ResourceUtil;

@RestController
@RequestMapping(value = "system")
public class RoleController {
	public static final Logger logger = LoggerFactory.getLogger(RoleController.class);
	
	@Autowired
	private RoleService roleService;
	@Autowired
	private ResourceService resourceService;
	
	@RequestMapping(value = "role_list")
	public ModelAndView role_list(ModelAndView view) {
		view.setViewName("system/role_list");
		return view;
	}

	@RequestMapping(value = "role_list_ajax", method = { RequestMethod.POST })
	public ResultMessage role_list_ajax(Role role, Page page) {
		List<Role> roleList = roleService.findRoleList(role, page);
		return new ResultMessage(ResponseEnum.M2000, roleList, page);
	}

	// 角色权限编辑
	@RequestMapping(value = "role_resource_edit")
	public ModelAndView role_resource_edit(ModelAndView view, Long id) {
		view.addObject("roleId", id);
		view.setViewName("system/role_resource_edit");
		return view;
	}
	
	@RequestMapping(value = "role_resource_edit_ajax", method = { RequestMethod.POST })
	public ResultMessage role_resource_edit_ajax(Long roleId) {
		Map<String, Object> data = new HashMap<>();
		Map<Long, Long> map = resourceService.findMapByIds(0, roleId);
		List<Resource> parentResourceList = resourceService.findResourceList(0L, 0);
		resourceService.findAllResourceList(parentResourceList, 0);
		ResourceUtil.markResource(parentResourceList, map);
		String list = JSONObject.toJSONString(parentResourceList);
		data.put("zTreeNodes", list.replaceAll("resourceName", "name").replaceAll("childResource", "nodes").replaceAll("hasResource", "checked"));
		return new ResultMessage(ResponseEnum.M2000, data);
	}

	@RequestMapping(value = "role_add")
	public ModelAndView role_add(ModelAndView view) {
		view.setViewName("system/role_add");
		return view;
	}

	@RequestMapping(value = "role_add_ajax", method = { RequestMethod.POST })
	public ResultMessage role_add_ajax(Role role) {
		try {
			roleService.insertSelective(role);
			return new ResultMessage(ResponseEnum.M2000);
		} catch (Exception e) {
			logger.error("角色新增失败,error_msg={}", e.getMessage());
		}
		return new ResultMessage(ResponseEnum.M4000);
	}

	@RequestMapping(value = "role_update")
	public ModelAndView role_update(ModelAndView view, Long id) {
		view.addObject("id", id);
		view.setViewName("system/role_edit");
		return view;
	}

	@RequestMapping(value = "role_update_ajax", method = { RequestMethod.POST })
	public ResultMessage role_update_ajax(Role role) {
		try {
			roleService.updateByPrimaryKeySelective(role);
			return new ResultMessage(ResponseEnum.M2000);
		} catch (Exception e) {
			logger.error("角色更新失败,error_msg={}", e.getMessage());
		}
		return new ResultMessage(ResponseEnum.M4000);
	}

	@RequestMapping(value = "role_detail_ajax", method = { RequestMethod.POST })
	public ResultMessage role_detail_ajax(Long id) {
		Map<String, Object> data = Maps.newHashMap();
		Role role = roleService.selectByPrimaryKey(id);
		data.put("role", role);
		return new ResultMessage(ResponseEnum.M2000, data);
	}
	
	// 角色权限更新
	@RequestMapping(value = "role_resource_update_ajax", method = { RequestMethod.POST })
	public ResultMessage role_resource_update_ajax(HttpServletRequest request) {
		try {
			Long id = ParamHelper.getLongParamter(request, "roleId", 0);
			Map<Long, Long> roleResourceMap = resourceService.findMapByIds(0, id);
			String idStrings = request.getParameter("resourceId");
			Long[] ids = null;
			if (idStrings == null) {
				System.out.println("ids不能为空");
			} else if (idStrings.trim().equals("")) {
				ids = new Long[0];
			} else {
				ids = ArrayUtil.toLongArray(idStrings, ",");
			}
			roleService.updateRoleResource(id, roleResourceMap, ids);
			return new ResultMessage(ResponseEnum.M2000);
		} catch (Exception e) {
			logger.error("修改角色权限失败,error_msg={}", e.getMessage());
		}
		return new ResultMessage(ResponseEnum.M4000);
	}
}
