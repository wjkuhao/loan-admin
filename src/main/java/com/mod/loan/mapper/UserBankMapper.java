package com.mod.loan.mapper;

import com.mod.loan.common.mapper.MyBaseMapper;
import com.mod.loan.model.UserBank;
import org.apache.ibatis.annotations.Param;

public interface UserBankMapper extends MyBaseMapper<UserBank> {

	UserBank selectOneByUid(@Param("uid") Long uid);

}