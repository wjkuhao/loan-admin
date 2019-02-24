package com.mod.loan.mapper;

import com.mod.loan.common.mapper.MyBaseMapper;
import com.mod.loan.model.UserAddressList;
import org.apache.ibatis.annotations.Param;

public interface UserAddressListMapper extends MyBaseMapper<UserAddressList> {

	UserAddressList selectOneByUid(@Param("uid") Long uid);

}