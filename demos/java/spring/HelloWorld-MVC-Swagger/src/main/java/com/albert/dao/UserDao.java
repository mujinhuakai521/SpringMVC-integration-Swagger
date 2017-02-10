package com.albert.dao;

import com.albert.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserDao {	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@SuppressWarnings("deprecation")
	public int getMatchCount(String userName, String password){
		String sql = "SELECT count(1) FROM t_user "
				+ " WHERE user_name = ? AND password = ? ";
		return jdbcTemplate.queryForInt(sql, new Object[]{userName, password});
	}
	
	public User findUserByUserName(final String userName){
		String sql =  "SELECT user_id, user_name, credits FROM t_user "
				+ " WHERE user_name = ? ";
		final User user = new User();
		jdbcTemplate.query(sql, new Object[]{userName}, new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException {
					user.setUserName(rs.getString("user_name"));
					user.setCredits(rs.getInt("credits"));
					user.setUsrid(rs.getInt("user_id"));
			}			
		});
		return user;
	}
	public void updateLoginInfo(User user){
		String sql =  "UPDATE t_user SET last_visit = ?, last_ip = ?, credits = ? "
				+ " WHERE user_id = ? ";
		jdbcTemplate.update(sql, new Object[]{user.getLastVisit(),
				user.getLastip(),user.getCredits(),user.getUsrid()});
	}
	
}
