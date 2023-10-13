package com.poscodx.jblog.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.poscodx.jblog.security.AuthUser;
import com.poscodx.jblog.service.BlogService;
import com.poscodx.jblog.service.CategoryService;
import com.poscodx.jblog.service.FileUploadService;
import com.poscodx.jblog.service.PostService;
import com.poscodx.jblog.service.UserService;
import com.poscodx.jblog.vo.BlogVo;
import com.poscodx.jblog.vo.CategoryVo;
import com.poscodx.jblog.vo.PostVo;
import com.poscodx.jblog.vo.UserVo;

@Controller
@RequestMapping(value = "/{id:^(?!assets).*}")
public class BlogController {
	
	@Autowired
	BlogService blogService;
	
	@Autowired
	PostService postService;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	private FileUploadService fileUploadService;
	
	@RequestMapping({"", "/{categoryNo}", "/{categoryNo}/{postNo}"})
	public String index(
			@PathVariable("id") String blogId,
			@PathVariable("categoryNo") Optional<Long> categoryNo,
			@PathVariable("postNo") Optional<Long> postNo,
			Model model) {
		
		Map<String, Object> map = new HashMap<>();
		BlogVo blogVo = blogService.findById(blogId);
		
		if(blogVo == null) {
			return "error/404";
		}
		
		List<CategoryVo> categoryList = categoryService.getAllContents(blogId);
		
		PostVo postVo = null;
		List<PostVo> postList = new ArrayList<>();
		
		if(categoryNo.isPresent()) {
			postList = postService.getAllContents(blogId, categoryNo.get());
		}else {
			if(!categoryList.isEmpty()) {
				postList = postService.getAllContents(blogId, categoryList.get(0).getNo());
			}
		}
		
		if(postNo.isPresent()) {
			postVo = postService.getPostByNo(postNo.get());
		}else {
			if(!postList.isEmpty()) {
				postVo = postList.get(0);
			}
		}
		
		map.put("postVo", postVo);
		map.put("vo", blogVo);
		map.put("blogId", blogId);
		map.put("categoryList", categoryList);
		map.put("postList", postList);
		
		model.addAllAttributes(map);
		
		return "blog/main";
	}
	
	@RequestMapping(value="/admin/basic", method=RequestMethod.GET)
	public String adminBasic(
			@AuthUser UserVo authUser,
			@PathVariable("id") String blogId, 
			Model model) {
		
		BlogVo blogVo = blogService.findById(blogId);
		model.addAttribute("vo", blogVo);
		
		UserVo userVo = userService.getUser(authUser.getId());
		model.addAttribute("userVo", userVo);
		
		return "blog/admin-basic";
	}
	
	@RequestMapping(value="/admin/basic", method=RequestMethod.POST)
	public String adminBasic(
			@AuthUser UserVo authUser,
			@PathVariable("id") String blogId,
			BlogVo blogVo, 
			MultipartFile file,
			Model model) {
		
		UserVo userVo = userService.getUser(authUser.getId());
		model.addAttribute("userVo", userVo);
		
		blogVo.setBlogId(blogId);
		String image = fileUploadService.restore(file);
		if(image != null) {
			blogVo.setImage(image);
		}
		blogService.update(blogVo);
		
		return "redirect:/" + blogId;
	}
	
	@RequestMapping(value="/admin/category", method=RequestMethod.GET)
	public String adminCategory(
			@AuthUser UserVo authUser,
			@PathVariable("id") String blogId,
			Model model) {

		UserVo userVo = userService.getUser(authUser.getId());
		BlogVo blogVo = blogService.findById(blogId);
		List<CategoryVo> list = categoryService.getAllContents(blogId);
		List<Integer> countList = new ArrayList<>();
		for(CategoryVo vo : list) {
			countList.add(postService.count(vo.getNo()));
		}
		
		model.addAttribute("vo", blogVo);
		model.addAttribute("userVo", userVo);
		model.addAttribute("list", list);
		model.addAttribute("blogId", blogId);
		model.addAttribute("countList", countList);
		
		return "blog/admin-category";
	}
	
	@RequestMapping(value="/admin/category", method=RequestMethod.POST)
	public String addCategory(
			@ModelAttribute @PathVariable("id") String blogId, 
			@ModelAttribute CategoryVo categoryVo,
			@ModelAttribute @AuthUser UserVo authUser,
			Model model) {
		
		UserVo userVo = userService.getUser(authUser.getId());
		model.addAttribute("userVo", userVo);
		
		categoryVo.setBlogId(blogId);
		
		categoryService.addCategory(categoryVo);
		
		return "redirect:/" + blogId + "/admin/category";
	}
	
	@RequestMapping(value="/admin/delete/{no}")
	@Transactional
	public String deleteCategory(
			@AuthUser UserVo authUser,
			@PathVariable("id") String blogId,
			@PathVariable("no") int no,
			Model model) {
		
		UserVo userVo = userService.getUser(authUser.getId());
		model.addAttribute("userVo", userVo);
		
		postService.delete(no);
		categoryService.remove(no);
		
		return "redirect:/" + blogId + "/admin/category";
	}
	
	@RequestMapping(value="/admin/write", method=RequestMethod.GET)
	public String write(
			@AuthUser UserVo authUser,
			@PathVariable("id") String blogId, 
			Model model) {
		
		UserVo userVo = userService.getUser(authUser.getId());
		BlogVo blogVo = blogService.findById(blogId);
		List<CategoryVo> list = categoryService.getAllContents(blogId);
		
		model.addAttribute("userVo", userVo);
		model.addAttribute("vo", blogVo);
		model.addAttribute("list", list);
		model.addAttribute("blogId", blogId);
		
		return "blog/admin-write";
	}
	
	@RequestMapping(value="/admin/write", method=RequestMethod.POST)
	public String write(
			@ModelAttribute PostVo postVo,
			@ModelAttribute @PathVariable("id") String blogId,
			@AuthUser UserVo authUser,
			Model model) {
		
		UserVo userVo = userService.getUser(authUser.getId());
		model.addAttribute("userVo", userVo);
		
		postService.addPost(postVo);
		
		return "redirect:/" + blogId;
	}
}