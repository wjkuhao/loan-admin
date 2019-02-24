package com.mod.loan.controller.market;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.mod.loan.common.enums.ResponseEnum;
import com.mod.loan.common.model.Page;
import com.mod.loan.common.model.ResultMessage;
import com.mod.loan.service.MarketService;
import com.mod.loan.util.ExcelUtil;
import com.mod.loan.util.TimeUtils;

@RestController
@RequestMapping(value = "market")
public class FlowController {

	public static final Logger log = LoggerFactory.getLogger(FlowController.class);
	
	@Autowired
	MarketService marketService;
	
	@RequestMapping(value = "flow_list")
	public ModelAndView flow_list(ModelAndView mv) {
		mv.setViewName("market/flow_list");
		return mv;
	}
	
	@RequestMapping(value = "flow_list_ajax")
	public ResultMessage flow_list_ajax(Long productId,@DateTimeFormat(pattern="yyyy-MM-dd") Date startDate,@DateTimeFormat(pattern="yyyy-MM-dd")Date endDate,Page page) {
		return new ResultMessage(ResponseEnum.M2000,marketService.findFlowList(productId, startDate, endDate, page),page);
	}
	
	@RequestMapping(value = "flow_export")
	public void flow_export(Long productId,@DateTimeFormat(pattern="yyyy-MM-dd") Date startDate,@DateTimeFormat(pattern="yyyy-MM-dd")Date endDate,HttpServletResponse response) {
		List<Map<String,Object>> data = marketService.findFlowList(productId, startDate, endDate, new Page(1000));
		String[] headers = new String[] { "产品", "流量", "类型", "日期"};
		// 设置插入值的名称
		String[] columns = new String[] { "product_name", "flow_uv", "product_type", "flow_date" };
		HSSFWorkbook workbook=new HSSFWorkbook();
		ExcelUtil.createSheet(workbook, "流量统计", headers, ExcelUtil.mapToArray(data, columns));
		ExcelUtil.excelExp(response, new DateTime().toString(TimeUtils.dateformat4), workbook);
	}
}
