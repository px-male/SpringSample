package com.example.demo.login.domain.repository.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.example.demo.login.domain.model.User;

public class UserRowMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO 自動生成されたメソッド・スタブ
		//戻り値のUserインスタンスを生成
		User user = new User();
		
		// ResultSetの取得結果をUserインタフェースにセット
		user.setUserId(rs.getString("user_Id"));
		user.setUserName(rs.getString("user_name"));
		user.setPassword(rs.getString("password"));
		user.setAge(rs.getInt("age"));
		user.setBirthday(rs.getDate("birthday"));
		user.setMarriage(rs.getBoolean("marriage"));
		user.setRole(rs.getString("role"));
		
		return user;
	}
}
