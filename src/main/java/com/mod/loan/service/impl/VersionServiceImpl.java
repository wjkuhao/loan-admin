package com.mod.loan.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mod.loan.common.mapper.BaseServiceImpl;
import com.mod.loan.common.model.Page;
import com.mod.loan.mapper.VersionMapper;
import com.mod.loan.model.Version;
import com.mod.loan.service.VersionService;

@Service
public class VersionServiceImpl extends BaseServiceImpl<Version, Long> implements VersionService {

	@Autowired
	private VersionMapper versionMapper;

	@Override
	public List<Map<String, Object>> findVersionList(Version version, Page page) {
		Map<String, Object> param = new HashMap<String, Object>();

		param.put("versionAlias", version.getVersionAlias());
		param.put("versionStatus", version.getVersionStatus());
		param.put("versionType", StringUtils.isNotEmpty(version.getVersionType()) ? version.getVersionType() : null);

		param.put("startIndex", page.getStartIndex());
		param.put("pageSize", page.getPageSize());
		page.setTotalCount(versionMapper.versionCount(param));
		return versionMapper.findVersionList(param);
	}

}