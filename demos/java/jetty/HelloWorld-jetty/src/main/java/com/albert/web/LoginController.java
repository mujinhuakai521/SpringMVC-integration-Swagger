package com.albert.web;

import com.albert.domain.User;
import com.albert.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
public class LoginController{
	@Autowired
	private UserService userService;
	
	@RequestMapping("/index.html")
	public String loginPage(){
		System.out.println("index.html");
		return "login";
	}
	@RequestMapping("/loginCheck.html")
	public ModelAndView loginCheck(HttpServletRequest request, LoginCommand loginCommand){
		boolean isValidUser = userService.hasMatchUsers(loginCommand.getUserName(), loginCommand.getPassword());
		if(!isValidUser){
			return new ModelAndView("login","error","用户或密码错误");
		} else{
			User user = userService.findUserByUserName(loginCommand.getUserName());
			user.setLastip(request.getRemoteAddr());
			user.setLastVisit(new Date());
			userService.loginSuccess(user);
			System.out.println(user.getUserName()+";"+user.getPassword());
			request.getSession().setAttribute("user", user);
			System.out.println(request.getSession().getAttribute("user"));
			return new ModelAndView("main");
		}
	}
}
