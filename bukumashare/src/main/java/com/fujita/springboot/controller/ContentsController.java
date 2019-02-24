package com.fujita.springboot.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


import com.fujita.springboot.entity.Contents;

@Controller
public class ContentsController {


	@Autowired
	HttpSession session;

	@RequestMapping(value="/goContentsCreate")
	public ModelAndView goUserCreate(@ModelAttribute("contentsCreate") Contents contents ,ModelAndView modelAndView) {

		if((String) session.getAttribute("loginFlg")=="1") {
			modelAndView.setViewName("/contentsCreate");
		}else {
			modelAndView.setViewName("redirect:/goLogin");
		}

		return modelAndView;
	}

}
