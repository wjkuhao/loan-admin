package com.mod.loan.mapper;

import java.util.List;
import java.util.Map;

import com.mod.loan.common.mapper.MyBaseMapper;
import com.mod.loan.model.Version;

public interface VersionMapper extends MyBaseMapper<Version> {

	// 多条件查询
	List<Map<String, Object>> findVersionList(Map<String, Object> param);

	int versionCount(Map<String, Object> param);

}