package com.albert.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController{

	private boolean validUser(String name, String password) {
		return name != null && password != null;
	}

	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView loginPage(){
		return new ModelAndView("web/loginPage");
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> doLogin(String name, String password) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("name", name);
		result.put("password", password);
		return result;
	}
}
