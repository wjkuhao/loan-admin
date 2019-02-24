package com.mod.loan.mapper;

import java.util.List;
import java.util.Map;

import com.mod.loan.common.mapper.MyBaseMapper;
import com.mod.loan.model.Merchant;

public interface MerchantMapper extends MyBaseMapper<Merchant> {

	int merchantCount(Map<String, Object> param);

	List<Map<String, Object>> findMerchantList(Map<String, Object> param);
}