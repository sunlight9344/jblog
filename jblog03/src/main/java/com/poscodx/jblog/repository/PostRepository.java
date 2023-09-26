package com.poscodx.jblog.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscodx.jblog.vo.PostVo;

@Repository
public class PostRepository {

	@Autowired
	SqlSession sqlSession;
	
	public void insert(PostVo postVo) {
		sqlSession.insert("post.insert", postVo);
	}

}
