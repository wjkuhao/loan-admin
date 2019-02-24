package com.mod.loan.mapper;

import java.util.List;
import java.util.Map;

import com.mod.loan.common.mapper.MyBaseMapper;
import com.mod.loan.model.User;

public interface UserMapper extends MyBaseMapper<User> {

	int userCount(Map<String, Object> param);

	List<Map<String, Object>> findUserList(Map<String, Object> param);

}