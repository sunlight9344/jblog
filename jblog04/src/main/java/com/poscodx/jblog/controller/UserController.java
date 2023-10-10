package com.poscodx.jblog.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.poscodx.jblog.service.BlogService;
import com.poscodx.jblog.service.CategoryService;
import com.poscodx.jblog.service.UserService;
import com.poscodx.jblog.vo.BlogVo;
import com.poscodx.jblog.vo.CategoryVo;
import com.poscodx.jblog.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BlogService blogService;
	
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login() {
		return "user/login";
	}

	@RequestMapping(value="/join", method=RequestMethod.GET)
	public String join(@ModelAttribute UserVo userVo) {
		return "user/join";
	}
	
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String join(
			@ModelAttribute @Valid UserVo uservo, 
			BindingResult result,
			String agreeProv,
			Model model) {
		
		UserVo tempUser = userService.getUser(uservo.getId());
		
		if(result.hasErrors()) {
			model.addAllAttributes(result.getModel());
			return "user/join";
		}
		
		if(!(tempUser == null)) {
			model.addAttribute("duplicateId", "중복된 ID 입니다.");
			return "user/join";
		}
		
		if(agreeProv == null) {
			model.addAttribute("errorAgreeProv", "약관에 동의해주세요");
			return "user/join";
		}
		
		BlogVo blogVo = new BlogVo();
		blogVo.setBlogId(uservo.getId());
		
		userService.join(uservo);
		blogService.add(blogVo);
		categoryService.addCategory(new CategoryVo("미분류", "미분류", uservo.getId()));
		
		return "redirect:/user/joinsuccess";
	}
	
	@RequestMapping(value="/joinsuccess", method=RequestMethod.GET)
	public String joinsuccess() {
		return "user/joinsuccess";
	}
	
	@RequestMapping("/checkUserIdExist/{blogId}")
	public String checkUserIdExist(@PathVariable("blogId") String blogId) {
		return "user/joinsuccess";
	}
}
