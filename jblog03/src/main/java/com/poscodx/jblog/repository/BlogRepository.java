package com.poscodx.jblog.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscodx.jblog.vo.BlogVo;

@Repository
public class BlogRepository {

	@Autowired
	SqlSession sqlSession;
	
	public void insert(String blog_id) {
		sqlSession.insert("blog.insert", blog_id);
	}

	public BlogVo findById(String blogId) {
		return sqlSession.selectOne("blog.selectById", blogId);
	}

}
