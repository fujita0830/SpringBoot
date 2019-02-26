package com.fujita.springboot.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fujita.springboot.entity.Account;
import com.fujita.springboot.entity.Contents;
import com.fujita.springboot.repository.ContentsRepository;

@Controller
public class ContentsController {


	@Autowired
	HttpSession session;

	@Autowired
	ContentsRepository contentsrepository;

	@RequestMapping(value="/goContentsCreate")
	public ModelAndView goUserCreate(@ModelAttribute("contentsCreate") Contents contents ,ModelAndView modelAndView) {

		if((String) session.getAttribute("loginFlg")=="1") {
			modelAndView.setViewName("/contentsCreate");
		}else {
			modelAndView.setViewName("redirect:/goLogin");
		}

		return modelAndView;
	}

	@RequestMapping(value="/contentsCreate", method = RequestMethod.POST)
	public ModelAndView contentsCreate(@ModelAttribute  Account account ,@ModelAttribute("contentsCreate")@Validated Contents contents,
			BindingResult result,
			ModelAndView modelAndView) {
		if((String) session.getAttribute("loginFlg")=="1") {

			if(!result.hasErrors()) {
				account=(Account) (session.getAttribute("account"));
				contents.setAccountId(account.getId());
				contents.setLoginId(account.getLoginId());
				contentsrepository.saveAndFlush(contents);
				Iterable<Contents> contentsList =contentsrepository.findByAccountId(account.getId());
				modelAndView.addObject("contentsList",contentsList);
				modelAndView.setViewName("/myPage");
			}else {
				modelAndView.setViewName("/contentsCreate");
			}

		}else {
			modelAndView.setViewName("redirect:/goLogin");
		}
		return modelAndView;
	}


	@RequestMapping(value="/contents")
	public ModelAndView contents(@ModelAttribute Contents contents,
			ModelAndView modelAndView) {

		contents=contentsrepository.findByContentsId(contents.getContentsId());

		if(Integer.parseInt(contents.getReadStatus())==1) {
			modelAndView.addObject("contents",contents);
			modelAndView.setViewName("/contents");

		}else {
			modelAndView.setViewName("redirect:/goLogin");
		}
		return modelAndView;
	}



}
