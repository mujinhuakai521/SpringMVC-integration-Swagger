package com.albert.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.albert.dao.LoginLogDao;
import com.albert.dao.UserDao;
import com.albert.domain.LoginLog;
import com.albert.domain.User;

@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private LoginLogDao loginLogDao;
	
	public boolean hasMatchUsers(String userName, String password){
		int matchCount = userDao.getMatchCount(userName, password);
		return matchCount>0;
	}
	public User findUserByUserName(String userName){
		return userDao.findUserByUserName(userName);
	}
	public void loginSuccess(User user){
		user.setCredits(5+user.getCredits());
		LoginLog loginLog = new LoginLog();
		loginLog.setUserId(user.getUsrid());
		loginLog.setIp(user.getLastip());
		loginLog.setLoginDate(user.getLastVisit());
		userDao.updateLoginInfo(user);
		loginLogDao.insertLoginLog(loginLog);
	}
}
