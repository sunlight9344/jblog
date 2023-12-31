package com.poscodx.jblog.repository;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscodx.jblog.vo.UserVo;

@Repository
public class UserRepository {

	@Autowired
	private SqlSession sqlSession;
	
	public void insert(UserVo uservo) {
		sqlSession.insert("user.insert", uservo);
	}

	public UserVo getUserByIdAndPassword(String id, String password) {
		Map<String, Object> map = new HashMap<>();
		map.put("id", id);
		map.put("password", password);
		return sqlSession.selectOne("user.getUserByIdAndPassword", map);
	}

	public UserVo getUserByIdAndPassword(String id) {
		return sqlSession.selectOne("user.getUserById", id);
	}

}
