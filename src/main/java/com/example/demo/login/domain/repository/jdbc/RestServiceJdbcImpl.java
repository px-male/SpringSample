package com.example.demo.login.domain.repository.jdbc;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.UserDao;
import com.example.demo.login.domain.service.RestService;


@Transactional
@Service
public class RestServiceJdbcImpl implements RestService {

	@Autowired
	@Qualifier("UserDaoJdbcImpl")
	UserDao dao;
	
	//1件登録用メソッド
	@Override
	public boolean insert(User user) {

		int result = dao.insertOne(user);
		
		if(result == 0) {
			return false;
		} else {
			return true;
		}
	}

	//1件検索用メソッド
	@Override
	public User selectOne(String userId) {
		// TODO 自動生成されたメソッド・スタブ
		return dao.selectOne(userId);
	}
	
	//全検索用メソッド
	@Override
	public List<User> selectMany() {
		// TODO 自動生成されたメソッド・スタブ
		return dao.selectMany();
	}

	//１件更新用メソッド
	@Override
	public boolean update(User user) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	//1件削除用メソッド
	@Override
	public boolean delete(String userId) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}
	
	
}
