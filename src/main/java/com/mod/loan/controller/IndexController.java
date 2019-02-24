package com.mod.loan.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class IndexController {
	@RequestMapping(value = "/")
	public ModelAndView index(ModelAndView mv) {
		mv.setViewName("redirect:/system/index");
		return mv;
	}
}
