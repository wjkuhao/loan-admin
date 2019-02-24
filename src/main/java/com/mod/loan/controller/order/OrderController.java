package com.mod.loan.controller.order;

import java.util.HashMap;
import java.util.Map;

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
import com.mod.loan.model.Order;
import com.mod.loan.service.OrderService;

@RestController
@RequestMapping(value = "order")
public class OrderController {

    public static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "order_list")
    public ModelAndView order_list(ModelAndView view) {
        view.setViewName("order/order_list");
        return view;
    }

    @RequestMapping(value = "order_list_ajax", method = {RequestMethod.POST})
    public ResultMessage order_list_ajax(Order order, String userPhone, String startTime, String endTime, String startRealRepayTime, String endRealRepayTime, String startCreateTime, String endCreateTime, String merchant, Page page) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("id", order.getId() != null ? order.getId() : null);
        param.put("merchant", StringUtils.isNotEmpty(merchant) ? merchant : null);
        param.put("userPhone", StringUtils.isNotEmpty(userPhone) ? userPhone : null);
        param.put("status", order.getStatus() != null ? order.getStatus() : null);
        param.put("borrowDay", order.getBorrowDay() != null ? order.getBorrowDay() : null);
        param.put("startTime", StringUtils.isBlank(startTime) ? null : startTime);
        param.put("endTime", StringUtils.isBlank(endTime) ? null : endTime);
        param.put("startRealRepayTime", StringUtils.isBlank(startRealRepayTime) ? null : startRealRepayTime);
        param.put("endRealRepayTime", StringUtils.isBlank(endRealRepayTime) ? null : endRealRepayTime);
        param.put("startCreateTime", StringUtils.isBlank(startCreateTime) ? null : startCreateTime);
        param.put("endCreateTime", StringUtils.isBlank(endCreateTime) ? null : endCreateTime);
        return new ResultMessage(ResponseEnum.M2000, orderService.findOrderList(param, page), page);
    }

}
