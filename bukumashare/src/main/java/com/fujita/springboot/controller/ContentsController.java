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
import org.springframework.transaction.annotation.Transactional;

import com.fujita.springboot.entity.Account;
import com.fujita.springboot.entity.Contents;
import com.fujita.springboot.repository.ContentsRepository;

@Controller
public class ContentsController {

	@Autowired
	HttpSession session;

	@Autowired
	ContentsRepository contentsrepository;

	@RequestMapping(value = "/goContentsCreate")
	public ModelAndView goUserCreate(@ModelAttribute("contentsCreate") Contents contents, ModelAndView modelAndView) {

		if ((String) session.getAttribute("loginFlg") == "1") {
			modelAndView.setViewName("/contentsCreate");
		} else {
			modelAndView.setViewName("redirect:/goLogin");
		}

		return modelAndView;
	}

	@RequestMapping(value = "/contentsCreate", method = RequestMethod.POST)
	public ModelAndView contentsCreate(@ModelAttribute Account account,
			@ModelAttribute("contentsCreate") @Validated Contents contents, BindingResult result,
			ModelAndView modelAndView) {
		if ((String) session.getAttribute("loginFlg") == "1") {

			if (!result.hasErrors()) {
				account = (Account) (session.getAttribute("account"));
				contents.setAccountId(account.getId());
				contents.setLoginId(account.getLoginId());
				contentsrepository.saveAndFlush(contents);
				modelAndView.setViewName("redirect:/myPage");
			} else {
				modelAndView.setViewName("/contentsCreate");
			}

		} else {
			modelAndView.setViewName("redirect:/goLogin");
		}
		return modelAndView;
	}

	@RequestMapping(value = "/contentsDetail")
	public ModelAndView contents(@ModelAttribute Contents contents, ModelAndView modelAndView) {

		contents = contentsrepository.findByContentsId(contents.getContentsId());

		if ((String) session.getAttribute("loginFlg") == "1") {
			modelAndView.addObject("contents", contents);
			modelAndView.setViewName("/contentsDetail");
		} else if (Integer.parseInt(contents.getShareStatus()) == 1) {
			modelAndView.addObject("contents", contents);
			modelAndView.setViewName("/contentsDetail");

		} else {
			modelAndView.setViewName("redirect:/goLogin");
		}

		return modelAndView;
	}

	@RequestMapping(value = "/contentsEdit")
	@Transactional(readOnly = false)
	public ModelAndView contentsEdit(@ModelAttribute Contents contents, ModelAndView modelAndView) {

		String readStatus = contents.getReadStatus();
		String shareStatus = contents.getShareStatus();

		contents = contentsrepository.findByContentsId(contents.getContentsId());

		contents.setReadStatus(readStatus);
		contents.setShareStatus(shareStatus);

		contentsrepository.saveAndFlush(contents);

		modelAndView.addObject("contents", contents);
		modelAndView.addObject("message", "success");
		modelAndView.setViewName("/contentsDetail");

		return modelAndView;

	}

	@RequestMapping(value = "/contentsDelete")
	@Transactional(readOnly = false)
	public ModelAndView contentsDelete(@ModelAttribute Contents contents, ModelAndView modelAndView) {

		contentsrepository.deleteByContentsId(contents.getContentsId());

		modelAndView.addObject("message", "delete");

		modelAndView.setViewName("redirect:/myPage");

		return modelAndView;

	}

}
