package com.mod.loan.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mod.loan.common.model.Page;
import com.mod.loan.mapper.MarketChannelMapper;
import com.mod.loan.mapper.MarketFlowMapper;
import com.mod.loan.mapper.MarketModuleMapper;
import com.mod.loan.mapper.MarketProductMapper;
import com.mod.loan.model.MarketChannel;
import com.mod.loan.model.MarketModule;
import com.mod.loan.model.MarketProduct;
import com.mod.loan.service.MarketService;

@Service
public class MarketServiceImpl implements MarketService {

    @Autowired
    MarketChannelMapper channelMapper;
    @Autowired
    MarketModuleMapper moduleMapper;
    @Autowired
    MarketProductMapper productMapper;
    @Autowired
    MarketFlowMapper flowMapper;

    @Override
    public List<MarketChannel> findChannelList(MarketChannel marketChannel, Page page) {
        Map<String, Object> param = new HashMap<>();
        param.put("startIndex", page.getStartIndex());
        param.put("pageSize", page.getPageSize());
        page.setTotalCount(channelMapper.selectCount(marketChannel));
        return channelMapper.findChannelList(param);
    }

    @Override
    public List<Map<String, Object>> findModuleList(MarketModule marketModule, Page page) {
        Map<String, Object> param = new HashMap<>();
        param.put("startIndex", page.getStartIndex());
        param.put("pageSize", page.getPageSize());
        page.setTotalCount(moduleMapper.selectCount(marketModule));
        return moduleMapper.findModuleList(param);
    }

    @Override
    public List<Map<String, Object>> findProductList(MarketProduct marketProduct, Page page) {
        Map<String, Object> param = new HashMap<>();
        param.put("startIndex", page.getStartIndex());
        param.put("pageSize", page.getPageSize());
        param.put("productType", marketProduct.getProductType());
        param.put("productStatus", marketProduct.getProductStatus());
        page.setTotalCount(productMapper.selectCount(marketProduct));
        return productMapper.findProductList(param);
    }

    @Override
    public List<Map<String, Object>> findFlowList(Long productId, Date startDate, Date endDate, Page page) {
        Map<String, Object> param = new HashMap<>();
        param.put("startIndex", page.getStartIndex());
        param.put("pageSize", page.getPageSize());
        param.put("productId", productId);
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        page.setTotalCount(flowMapper.countFlow(param));
        List<Map<String, Object>> mapList = flowMapper.findFlowList(param);
        for (Map<String, Object> map : mapList) {
            if (map.get("product_type").equals(1)) {
                map.put("product_type", "内部");
            }
            if (map.get("product_type").equals(2)) {
                map.put("product_type", "外部");
            }
        }
        return mapList;
    }


}
