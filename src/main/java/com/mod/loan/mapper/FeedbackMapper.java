package com.mod.loan.mapper;

import com.mod.loan.common.mapper.MyBaseMapper;
import com.mod.loan.model.Feedback;

import java.util.List;
import java.util.Map;

public interface FeedbackMapper extends MyBaseMapper<Feedback> {

	int feedbackCount(Map<String, Object> param);

	List<Map<String, Object>> findFeedbackList(Map<String, Object> param);

}