package com.mod.loan.mapper;

import java.util.List;
import java.util.Map;

import com.mod.loan.common.mapper.MyBaseMapper;
import com.mod.loan.model.MarketProduct;

public interface MarketProductMapper extends MyBaseMapper<MarketProduct> {

	List<Map<String, Object>> findProductList(Map<String, Object> param);
}