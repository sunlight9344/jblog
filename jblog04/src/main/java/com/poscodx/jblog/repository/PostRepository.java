package com.poscodx.jblog.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	public List<PostVo> findAll(String blogId, long categoryNo) {
		Map<String, Object> map = new HashMap<>();
		map.put("blogId", blogId);
		map.put("categoryNo", categoryNo);
		return sqlSession.selectList("post.findAll", map);
	}

	public PostVo findByNo(Long postNo) {
		return sqlSession.selectOne("post.findByNo", postNo);
	}
	
	public void delete(int categoryNo){
		sqlSession.delete("post.deleteByNo", categoryNo);
	}

	public Integer count(int no) {
		return sqlSession.selectOne("post.count", no);
	}

}
