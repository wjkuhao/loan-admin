package com.mod.loan.mapper;

import java.util.List;
import java.util.Map;

import com.mod.loan.common.mapper.MyBaseMapper;
import com.mod.loan.model.MarketFlow;

public interface MarketFlowMapper extends MyBaseMapper<MarketFlow> {
	
	List<Map<String, Object>> findFlowList(Map<String, Object> param);
	
	int countFlow(Map<String, Object> param);
}