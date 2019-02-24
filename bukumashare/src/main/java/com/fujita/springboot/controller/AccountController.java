package com.fujita.springboot.controller;

import java.security.PublicKey;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.fujita.springboot.entity.Account;
import com.fujita.springboot.repository.AccountRepository;

import ch.qos.logback.core.net.LoginAuthenticator;


@Controller
public class AccountController {


	@Autowired
	HttpSession session;

	@Autowired
	AccountRepository accountrepository;

	@RequestMapping(value="/",method = RequestMethod.GET)
	public ModelAndView index(ModelAndView modelAndView) {
		modelAndView.setViewName("/index");
		Iterable<Account> list = accountrepository.findAll();
		modelAndView.addObject("datalist",list);

		return modelAndView;
	}

	@RequestMapping(value="/goLogin")
		public ModelAndView gologin(@ModelAttribute("loginForm") Account account ,ModelAndView modelAndView) {
			modelAndView.setViewName("/login");
			return modelAndView;
		}

	@RequestMapping(value="login")
	public ModelAndView LoginAuthenticator(@ModelAttribute("loginForm")@Validated Account account,
			BindingResult result,ModelAndView modelAndView) {

		if(!result.hasErrors()) {

			if(accountrepository.existsByLoginIdAndLoginPassword(account.getLoginId() ,account.getLoginPassword())){

				accountrepository.findByLoginIdAndLoginPassword(account.getLoginId(), account.getLoginPassword());
				account.setLoginFlg("1");
				session.setAttribute("account", account);
				session.setAttribute("loginFlg", account.getLoginFlg());

				modelAndView.setViewName("/myPage");
			}else {

				modelAndView.addObject("ErrorMessage", "existsLoginIdAndLoginPassword");
				modelAndView.setViewName("/login");
			}

		}else {
			modelAndView.setViewName("/login");
		}
		return modelAndView;
	}


	@RequestMapping(value = "/goUserCreate")
	public ModelAndView gouserCreate(@ModelAttribute("userCreate") Account account,ModelAndView modelAndView) {
		modelAndView.setViewName("/userCreate");
		return modelAndView;
	}

	@RequestMapping(value = "/userCreate", method = RequestMethod.POST)
	public ModelAndView userCreate(@ModelAttribute("userCreate") @Validated  Account account,
			BindingResult result, ModelAndView modelAndView) {
		if(!result.hasErrors()) {

			if(accountrepository.existsByLoginId(account.getLoginId())) {
				modelAndView.addObject("ErrorMessage", "existsLoginId");
				modelAndView.setViewName("/userCreate");
			}else {
				accountrepository.saveAndFlush(account);
				modelAndView.setViewName("redirect:/");
			}

		}else {
			modelAndView.setViewName("/userCreate");
		}
		return modelAndView;
	}


	@RequestMapping(value ="/myPage")
	public ModelAndView myPage(ModelAndView modelAndView) {

		if((String) session.getAttribute("loginFlg")=="1") {
			modelAndView.setViewName("/myPage");
		}else {
			modelAndView.setViewName("redirect:/goLogin");
		}

		return modelAndView;

	}



}
