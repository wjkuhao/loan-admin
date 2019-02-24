package com.mod.loan.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mod.loan.common.model.Page;
import com.mod.loan.model.MarketChannel;
import com.mod.loan.model.MarketModule;
import com.mod.loan.model.MarketProduct;

public interface MarketService {

	List<MarketChannel>  findChannelList(MarketChannel marketChannel,Page page);
	
	List<Map<String, Object>>  findModuleList(MarketModule marketModule,Page page);
	
	List<Map<String, Object>>  findProductList(MarketProduct marketProduct,Page page);
	
	List<Map<String, Object>>  findFlowList(Long productId,Date startDate,Date endDate ,Page page);
}
