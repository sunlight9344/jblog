package com.poscodx.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.jblog.repository.UserRepository;
import com.poscodx.jblog.vo.UserVo;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public void join(UserVo uservo) {
		userRepository.insert(uservo);
	}

	public UserVo getUser(String id, String password) {
		return userRepository.getUserByIdAndPassword(id, password);
	}

}
