package com.mod.loan.service;

import com.mod.loan.common.mapper.BaseService;
import com.mod.loan.common.model.Page;
import com.mod.loan.model.Order;

import java.util.List;
import java.util.Map;

public interface OrderService extends BaseService<Order, Long> {


	// 多条件查找
	List<Map<String, Object>> findOrderList(Map<String, Object> param, Page page);

	List<Map<String, Object>> exportReport(Map<String, Object> param);



}
