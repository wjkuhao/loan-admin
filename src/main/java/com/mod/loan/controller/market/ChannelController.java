package com.mod.loan.controller.market;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.mod.loan.common.enums.ResponseEnum;
import com.mod.loan.common.model.Page;
import com.mod.loan.common.model.ResultMessage;
import com.mod.loan.mapper.MarketChannelMapper;
import com.mod.loan.model.MarketChannel;
import com.mod.loan.service.MarketService;

@RestController
@RequestMapping(value = "market")
public class ChannelController {
	public static final Logger logger = LoggerFactory.getLogger(ChannelController.class);
	
	@Autowired
	MarketService marketService;
	@Autowired
	MarketChannelMapper channelMapper;
	
	@RequestMapping(value = "channel_list")
	public ModelAndView channel_list(ModelAndView view) {
		view.setViewName("market/channel_list");
		return view;
	}

	@RequestMapping(value = "channel_list_ajax")
	public ResultMessage channel_list_ajax(MarketChannel marketChannel, Page page) {
		return new ResultMessage(ResponseEnum.M2000, marketService.findChannelList(marketChannel, page), page);
	}

	@RequestMapping(value = "channel_able_list")
	public ResultMessage channel_able_list() {
		MarketChannel record=new MarketChannel();
		record.setChannelStatus(1);
		return new ResultMessage(ResponseEnum.M2000, channelMapper.select(record));
	}
	
	@RequestMapping(value = "channel_edit")
	public ModelAndView channel_edit(ModelAndView view, Long id) {
		if (id == null) {
			view.setViewName("market/channel_add");
		} else {
			view.setViewName("market/channel_edit");
			view.addObject("id", id);
		}
		return view;
	}

	@RequestMapping(value = "channel_edit_ajax", method = RequestMethod.POST)
	public ResultMessage channel_edit_ajax(MarketChannel marketChannel) {
		if (marketChannel.getId() == null) {
			channelMapper.insertSelective(marketChannel);
		} else {
			MarketChannel channel = channelMapper.selectByPrimaryKey(marketChannel.getId());
			channel.setProductId(marketChannel.getProductId());
			channelMapper.updateByPrimaryKey(channel);
		}
		return new ResultMessage(ResponseEnum.M2000);
	}

	@RequestMapping(value = "channel_detail_ajax")
	public ResultMessage channel_detail_ajax(Long id) {
		return new ResultMessage(ResponseEnum.M2000, channelMapper.selectByPrimaryKey(id));
	}
}
