package com.albert.web;

import com.albert.domain.User;
import com.albert.service.UserService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
public class LoginController{
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/login.html",method = RequestMethod.GET)
   @ApiOperation(value = "获取用户登陆界面",httpMethod = "GET")
	public String loginPage(){
		System.out.println("login.html");
		return "login";
	}
	@RequestMapping(value = "/loginCheck.html",method = RequestMethod.POST)
    @ApiOperation(value = "用户登陆账号检查",httpMethod = "POST")
	public ModelAndView loginCheck(HttpServletRequest request, @RequestBody LoginCommand loginCommand){
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
