package com.albert.dao;

import com.albert.domain.LoginLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.albert.domain.User;

@Repository
public class LoginLogDao {	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void insertLoginLog(LoginLog loginlog){
		String sql =  "INSERT INTO t_login_log(user_id, ip, login_datetime) "
				+ " VALUES(?,?,?) ";
		final User user = new User();
		jdbcTemplate.update(sql, new Object[]{loginlog.getUserId(), loginlog.getIp(), loginlog.getLoginDate()});
	}
	
}
