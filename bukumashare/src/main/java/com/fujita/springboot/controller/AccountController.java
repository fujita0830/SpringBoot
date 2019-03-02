package com.fujita.springboot.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.servlet.ModelAndView;

import com.fujita.springboot.entity.Account;
import com.fujita.springboot.entity.Contents;
import com.fujita.springboot.repository.AccountRepository;
import com.fujita.springboot.repository.ContentsRepository;

@Controller
public class AccountController {

	@Autowired
	HttpSession session;

	@Autowired
	AccountRepository accountrepository;

	@Autowired
	ContentsRepository contentsrepository;

	@RequestMapping(value = "/")
	public ModelAndView top(ModelAndView modelAndView) {
		modelAndView.setViewName("top");
		return modelAndView;
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index(ModelAndView modelAndView, @PageableDefault(size = 3) Pageable pageable) {
		modelAndView.setViewName("index");
		Page<Contents> contentsList = contentsrepository.findByshareStatusOrderByInsertDateDesc("1", pageable);
		modelAndView.addObject("contentsList", contentsList);
		return modelAndView;
	}

	@RequestMapping(value = "/goLogin")
	public ModelAndView gologin(@ModelAttribute("loginForm") Account account, ModelAndView modelAndView) {

		if ((String) session.getAttribute("loginFlg") == "1") {
			modelAndView.setViewName("myPage");
		}
		modelAndView.setViewName("login");
		return modelAndView;
	}

	@RequestMapping(value = "/login")
	public ModelAndView LoginAuthenticator(@ModelAttribute("loginForm") @Validated Account account,
			@ModelAttribute("contents") Contents contents, BindingResult result, ModelAndView modelAndView) {

		if (!result.hasErrors()) {

			if (accountrepository.existsByLoginIdAndLoginPassword(account.getLoginId(), account.getLoginPassword())) {

				account = accountrepository.findByLoginIdAndLoginPassword(account.getLoginId(),
						account.getLoginPassword());
				account.setLoginFlg("1");
				accountrepository.saveAndFlush(account);
				session.setAttribute("account", account);
				session.setAttribute("id", account.getId());
				session.setAttribute("loginFlg", account.getLoginFlg());

				modelAndView.setViewName("redirect:myPage");
			} else {

				modelAndView.addObject("ErrorMessage", "existsLoginIdAndLoginPassword");
				modelAndView.setViewName("login");
			}

		} else {
			modelAndView.setViewName("login");
		}
		return modelAndView;
	}

	@RequestMapping(value = "/logout")
	public ModelAndView logout(@ModelAttribute Account account, ModelAndView modelAndView) {

		if ((String) session.getAttribute("loginFlg") == "1") {
			account = accountrepository.findById((long) session.getAttribute("id"));
			account.setLoginFlg("0");
			accountrepository.saveAndFlush(account);
			session.invalidate();
			modelAndView.setViewName("top");
		} else {
			modelAndView.setViewName("redirect:goLogin");
		}
		return modelAndView;
	}

	@RequestMapping(value = "/goUserCreate")
	public ModelAndView gouserCreate(@ModelAttribute("userCreate") Account account, ModelAndView modelAndView) {
		modelAndView.setViewName("userCreate");
		return modelAndView;
	}

	@RequestMapping(value = "/userCreate", method = RequestMethod.POST)
	public ModelAndView userCreate(@ModelAttribute("userCreate") @Validated Account account, BindingResult result,
			ModelAndView modelAndView) {
		if (!result.hasErrors()) {

			if (accountrepository.existsByLoginId(account.getLoginId())) {
				modelAndView.addObject("ErrorMessage", "existsLoginId");
				modelAndView.setViewName("userCreate");
			} else {
				accountrepository.saveAndFlush(account);
				modelAndView.setViewName("top");
			}

		} else {
			modelAndView.setViewName("userCreate");
		}
		return modelAndView;
	}

	@RequestMapping(value = "/myPage")
	public ModelAndView myPage(@ModelAttribute("loginForm") Account account,
			@ModelAttribute("contents") Contents contents, @PageableDefault(size = 3) Pageable pageable,
			ModelAndView modelAndView) {

		if ((String) session.getAttribute("loginFlg") == "1") {

			if (!(contents.getReadStatus() == null) && Integer.parseInt(contents.getReadStatus()) == 0) {

				account = (Account) (session.getAttribute("account"));
				Page<Contents> contentsList = contentsrepository.findByAccountIdAndReadStatusOrderByInsertDateDesc(
						account.getId(), contents.getReadStatus(), pageable);
				modelAndView.addObject("contentsList", contentsList);
				modelAndView.setViewName("myPage");

			} else if (!(contents.getReadStatus() == null) && Integer.parseInt(contents.getReadStatus()) == 1) {
				account = (Account) (session.getAttribute("account"));
				Page<Contents> contentsList = contentsrepository.findByAccountIdAndReadStatusOrderByInsertDateDesc(
						account.getId(), contents.getReadStatus(), pageable);
				modelAndView.addObject("contentsList", contentsList);
				modelAndView.setViewName("myPage");
			} else {

				account = (Account) (session.getAttribute("account"));
				Page<Contents> contentsList = contentsrepository.findByAccountIdOrderByInsertDateDesc(account.getId(),
						pageable);
				modelAndView.addObject("contentsList", contentsList);
				modelAndView.setViewName("myPage");
			}

		} else {
			modelAndView.setViewName("redirect:goLogin");
		}

		return modelAndView;

	}

}
