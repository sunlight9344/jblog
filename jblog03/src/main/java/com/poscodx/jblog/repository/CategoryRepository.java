package com.poscodx.jblog.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscodx.jblog.vo.CategoryVo;

@Repository
public class CategoryRepository {
	
	@Autowired
	SqlSession sqlSession;

	public List<CategoryVo> selectAll(String blogId) {
		return sqlSession.selectList("category.selectAll", blogId);
	}

	public void delete(int no) {
		sqlSession.delete("category.deleteByNo", no);
	}

	public void insert(CategoryVo categoryVo) {
		sqlSession.insert("category.insert", categoryVo);
	}

}
