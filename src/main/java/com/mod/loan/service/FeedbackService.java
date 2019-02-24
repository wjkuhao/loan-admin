package com.mod.loan.service;

import com.mod.loan.common.mapper.BaseService;
import com.mod.loan.common.model.Page;
import com.mod.loan.model.Feedback;

import java.util.List;
import java.util.Map;

public interface FeedbackService extends BaseService<Feedback, Long> {

	List<Map<String, Object>> findFeedbackList(Map<String, Object> param, Page page);

}