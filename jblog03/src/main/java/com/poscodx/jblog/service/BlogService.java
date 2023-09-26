package com.poscodx.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.jblog.repository.BlogRepository;
import com.poscodx.jblog.vo.BlogVo;

@Service
public class BlogService {
	
	@Autowired
	BlogRepository blogRepository;

	public void add(String id) {
		blogRepository.insert(id);
	}

	public BlogVo findById(String blogId) {
		return blogRepository.findById(blogId);
	}
	
}
