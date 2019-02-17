package com.fujita.test.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fujita.test.entity.LoginUser;
import com.fujita.test.repository.LoginUserRepository;


@Controller
public class LoginController {

	@Autowired
	LoginUserRepository repository;

	@RequestMapping(value="/",method=RequestMethod.GET)
	public ModelAndView index(@ModelAttribute("formModel") LoginUser loginUser, ModelAndView modelAndView) {
		modelAndView.setViewName("index");
		Iterable<LoginUser> list = repository.findAll();
		modelAndView.addObject("datalist", list);

		return modelAndView;
	}

	@RequestMapping(value="/",method=RequestMethod.POST)
	@Transactional(readOnly=false)
	public ModelAndView regist(@ModelAttribute("formModel") LoginUser loginUser, ModelAndView modelAndView){
		repository.saveAndFlush(loginUser);
		return new ModelAndView("redirect:/");
	}
}
