package com.mod.loan.mapper;

import com.mod.loan.common.mapper.MyBaseMapper;
import com.mod.loan.model.Manager;
import com.mod.loan.model.MerchantQuotaConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MerchantQuotaConfigMapper extends MyBaseMapper<MerchantQuotaConfig> {

	int MerchantQuotaConfigCount(Map<String, Object> param);

	List<Map<String, Object>> findMerchantQuotaConfigList(Map<String, Object> param);


}