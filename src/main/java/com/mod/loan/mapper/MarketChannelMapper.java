package com.mod.loan.mapper;

import java.util.List;
import java.util.Map;

import com.mod.loan.common.mapper.MyBaseMapper;
import com.mod.loan.model.MarketChannel;

public interface MarketChannelMapper extends MyBaseMapper<MarketChannel> {
	
	List<MarketChannel> findChannelList(Map<String, Object> param);
}