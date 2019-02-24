package com.mod.loan.mapper;

import java.util.List;
import java.util.Map;

import com.mod.loan.common.mapper.MyBaseMapper;
import com.mod.loan.model.MarketModule;

public interface MarketModuleMapper extends MyBaseMapper<MarketModule> {

	List<Map<String, Object>> findModuleList(Map<String, Object> param);
}