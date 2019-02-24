package com.mod.loan.mapper;

import java.util.List;

import com.mod.loan.common.mapper.MyBaseMapper;
import com.mod.loan.model.MerchantResource;

public interface MerchantResourceMapper extends MyBaseMapper<MerchantResource> {

	List<MerchantResource> findMerchantResourceList(MerchantResource resource);

}
