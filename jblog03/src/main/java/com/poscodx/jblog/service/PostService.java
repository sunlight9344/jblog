package com.poscodx.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.jblog.repository.PostRepository;
import com.poscodx.jblog.vo.PostVo;

@Service
public class PostService {

	@Autowired
	PostRepository postRepository;
	
	public void addPost(PostVo postVo) {
		postRepository.insert(postVo);
	}

	public List<PostVo> getAllContents(String blogId) {
		return postRepository.findAll(blogId);
	}

}
