package com.mod.loan.mapper;


import com.mod.loan.common.mapper.MyBaseMapper;
import com.mod.loan.model.OrderRiskInfo;

import java.util.Map;

public interface OrderRiskInfoMapper extends MyBaseMapper<OrderRiskInfo> {

    Integer selectCountNow(Map riskMap);
}