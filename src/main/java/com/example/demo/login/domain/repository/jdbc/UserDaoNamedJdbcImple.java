package com.example.demo.login.domain.repository.jdbc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.UserDao;

@Repository("UserDaoNamedJdbcImple") 
public class UserDaoNamedJdbcImple implements UserDao {
	
	@Autowired
	private NamedParameterJdbcTemplate jdbc;
	
	//Userテーブルの件数を取得
	@Override
	public int count() {
		
		//SQL文
		String sql = "SELECT count(*) FROM m_user";
		
		//パラメータ生成
		SqlParameterSource params = new MapSqlParameterSource();
		
		//全件取得してカウント
		return jdbc.queryForObject(sql, params, Integer.class);
			
	}
	
	//ユーザテーブルにデータを１件insert
	@Override
	public int insertOne(User user) {
		//SQL文
		String sql = "INSERT INTO m_user(user_id,"
				+ " password,"
				+ " user_name,"
				+ " birthday,"
				+ " age,"
				+ " marriage,"
				+ " role)"
				+ "VALUES(:userId,"
				+ " :password,"
				+ " :userName,"
				+ " :birthday,"
				+ " :age,"
				+ " :marriage,"
				+ " :role)"	;
		
		//パラメータ
		SqlParameterSource params = new MapSqlParameterSource()
				.addValue("userId", user.getUserId())
				.addValue("password", user.getPassword())
				.addValue("userName", user.getUserName())
				.addValue("birthday", user.getBirthday())
				.addValue("age", user.getAge())
				.addValue("marriage", user.isMarriage())
				.addValue("role", user.getRole());
		
		//SQL実行
		return jdbc.update(sql, params);
	}
	
	//Userテーブルのデータ１件取得
	@Override
	public User selectOne(String userId) {
		
		//SQL文
		String sql = "SELECT * FROM m_user where user_id = :userId";
		
		//パラメータ
		SqlParameterSource params = new MapSqlParameterSource().addValue("userId", userId);
		
		//SQL実行
		Map<String, Object> map = jdbc.queryForMap(sql, params);
				
		//結果返却用のインスタンスを生成
		User user = new User();
		
		//取得データをインスタンスにセットしていく
		user.setUserId((String)map.get("user_id"));
		user.setUserName((String)map.get("user_name"));
		user.setPassword((String)map.get("password"));
		user.setAge((Integer)map.get("age"));
		user.setBirthday((Date)map.get("birthday"));
		user.setMarriage((Boolean)map.get("marriage"));
		user.setRole((String)map.get("role"));
		
		return user;
	}
	
	//Userデータの全データを取得
	@Override
	public List<User> selectMany() {
		
		//SQL文
		String sql = "SELECT * FROM m_user";
		
		//パラメータ
		SqlParameterSource params = new MapSqlParameterSource();
		
		//SQL実行
		List<Map<String, Object>> getList  = jdbc.queryForList(sql, params);
		
		//結果返却用のリスト
		List<User> userList = new ArrayList<>();
		
		//取得データ分Loop
		for(Map<String, Object> map: getList) {
			
			//ユーザインスタンスの生成
			User user = new User();
			
			user.setUserId((String)map.get("user_id"));
			user.setPassword((String)map.get("password"));
			user.setUserName((String)map.get("user_name"));
			user.setAge((Integer)map.get("age"));
			user.setBirthday((Date)map.get("birthday"));
			user.setMarriage((Boolean)map.get("marriage"));
			user.setRole((String)map.get("role"));
			
			//Listに追加
			userList.add(user);
		}
		return userList;
	}
	
	//Userテーブルを１件更新
	@Override
	public int updateOne(User user) {
		
		//SQL分
		String sql = "UPDATE M_USER"
				+ " SET"
				+ " password = :password,"
				+ " user_name = :user_name,"
				+ " age = :age,"
				+ " birthday = :birthday,"
				+ " marriage = :marriage"
				+ " WHERE user_id = :userId";
		
		//パラメータ作成
		SqlParameterSource params = new MapSqlParameterSource()
				.addValue("password", user.getPassword())
				.addValue("user_name", user.getUserName())
				.addValue("age", user.getAge())
				.addValue("birthday", user.getBirthday())
				.addValue("marriage", user.isMarriage())
				.addValue("userId", user.getUserId());
		
		//SQL実行
		return jdbc.update(sql, params);
	}
	
	//Userテーブルを１件削除
	@Override
	public int deleteOne(String userId) {
	
		//SQL文
		String sql = "DELETE FROM m_user WHERE user_id = :userId";
	
		//パラメータ作成
		SqlParameterSource params = new MapSqlParameterSource()
				.addValue("userId", userId);
		
		//SQL実行
		int rowNumber = jdbc.update(sql, params);
		
		return rowNumber;
	}
	
	//SQL取得結果をサーバにCSVで保存する
	@Override
	public void userCsvOut() {
		
		//M_USERテーブルのデータを全件取得する
		String sql = "SELECT * FROM m_user";
		
		//ResultSetExtractorの生成
		UserRowCallbackHandler handler = new UserRowCallbackHandler();
		
		//クエリー実行&CSV出力
		jdbc.query(sql, handler);
	}
	
}