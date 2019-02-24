package com.mod.loan.mapper;

import com.mod.loan.common.mapper.MyBaseMapper;
import com.mod.loan.model.UserDevice;
import org.apache.ibatis.annotations.Param;

public interface UserDeviceMapper extends MyBaseMapper<UserDevice> {
	
	UserDevice selectOneByUid(@Param("uid") Long uid);
	
}