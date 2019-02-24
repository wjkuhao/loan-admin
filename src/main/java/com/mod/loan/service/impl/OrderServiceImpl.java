package com.mod.loan.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mod.loan.common.mapper.BaseServiceImpl;
import com.mod.loan.common.model.Page;
import com.mod.loan.mapper.OrderMapper;
import com.mod.loan.model.Order;
import com.mod.loan.service.OrderService;

@Service
public class OrderServiceImpl extends BaseServiceImpl<Order, Long> implements OrderService {

    @Autowired
    private OrderMapper orderMapper;


    @Override
    public List<Map<String, Object>> findOrderList(Map<String, Object> param, Page page) {
        param.put("startIndex", page.getStartIndex());
        param.put("pageSize", page.getPageSize());
        page.setTotalCount(orderMapper.orderCount(param));
        return orderMapper.findOrderList(param);
    }

    @Override
    public List<Map<String, Object>> exportReport(Map<String, Object> param) {
        List<Map<String, Object>> orderList = orderMapper.exportReport(param);
        for (Map<String, Object> map : orderList) {
            if (map.get("user_type").equals(1)) {
                map.put("user_type", "新客");
            }
            if (map.get("user_type").equals(2)) {
                map.put("user_type", "次新");
            }
            if (map.get("user_type").equals(3)) {
                map.put("user_type", "老客");
            }
            if (map.get("status").equals(11)) {
                map.put("status", "机审中");
            }
            if (map.get("status").equals(12)) {
                map.put("status", "等待复审");
            }
            if (map.get("status").equals(21)) {
                map.put("status", "待放款");
            }
            if (map.get("status").equals(22)) {
                map.put("status", "放款中");
            }
            if (map.get("status").equals(23)) {
                map.put("status", "放款失败");
            }
            if (map.get("status").equals(31)) {
                map.put("status", "还款中");
            }
            if (map.get("status").equals(32)) {
                map.put("status", "还款确认中");
            }
            if (map.get("status").equals(33)) {
                map.put("status", "逾期");
            }
            if (map.get("status").equals(34)) {
                map.put("status", "坏账");
            }
            if (map.get("status").equals(41)) {
                map.put("status", "已结清");
            }
            if (map.get("status").equals(42)) {
                map.put("status", "逾期还款");
            }
            if (map.get("status").equals(51)) {
                map.put("status", "自动审核失败");
            }
            if (map.get("status").equals(52)) {
                map.put("status", "复审失败");
            }
            if (map.get("status").equals(53)) {
                map.put("status", "取消");
            }
        }
        return orderList;
    }


}
