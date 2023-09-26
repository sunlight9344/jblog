package com.poscodx.jblog.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.poscodx.jblog.service.BlogService;
import com.poscodx.jblog.service.CategoryService;
import com.poscodx.jblog.service.FileUploadService;
import com.poscodx.jblog.service.PostService;
import com.poscodx.jblog.vo.BlogVo;
import com.poscodx.jblog.vo.CategoryVo;
import com.poscodx.jblog.vo.PostVo;

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
	private FileUploadService fileUploadService;
	
	@RequestMapping({"", "/{categoryNo}", "/{categoryNo}/{postNo}"})
	public String index(
			@PathVariable("id") String blogId,
			@PathVariable("categoryNo") Optional<Long> categoryNoTemp,
			@PathVariable("postNo") Optional<Long> postNo,
			Model model) {
		
		Map<String, Object> map = new HashMap<>();
		BlogVo blogVo = blogService.findById(blogId);
		List<CategoryVo> categoryList = categoryService.getAllContents(blogId);
		
		PostVo postVo = null;
		long categoryNo = 2;
		
		if(categoryNoTemp.isPresent()) {
			categoryNo = categoryNoTemp.get();
		}
		
		List<PostVo> postList = postService.getAllContents(blogId, categoryNo);
		
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
			@PathVariable("id") String blogId, 
			Model model) {
		
		BlogVo blogVo = blogService.findById(blogId);
		model.addAttribute("vo", blogVo);
		
		return "blog/admin-basic";
	}
	
	@RequestMapping(value="/admin/basic", method=RequestMethod.POST)
	public String adminBasic(
			@PathVariable("id") String blogId,
			BlogVo blogVo, 
			MultipartFile file) {
		
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
			@PathVariable("id") String blogId,
			Model model) {
		
		List<CategoryVo> list = categoryService.getAllContents(blogId);
		model.addAttribute("list", list);
		model.addAttribute("blogId", blogId);
		List<Integer> countList = new ArrayList<>();
		for(CategoryVo vo : list) {
			countList.add(postService.count(vo.getNo()));
		}
		model.addAttribute("countList", countList);
		return "blog/admin-category";
	}
	
	@RequestMapping(value="/admin/delete/{no}")
	public String deleteCategory(
			@PathVariable("id") String blogId,
			@PathVariable("no") int no) {
		
		categoryService.remove(no);
//		System.out.println("------------>" + no);
		
		return "redirect:/" + blogId + "/admin/category";
	}
	
	@RequestMapping(value="/admin/addCategory")
	public String addCategory(@PathVariable("id") String blogId, CategoryVo categoryVo) {
		
		categoryVo.setBlogId(blogId);
		
		categoryService.addCategory(categoryVo);
		
		return "redirect:/" + blogId + "/admin/category";
	}
	
	@RequestMapping(value="/admin/write", method=RequestMethod.GET)
	public String write(
			@PathVariable("id") String blogId, 
			Model model) {
		List<CategoryVo> list = categoryService.getAllContents(blogId);
		model.addAttribute("list", list);
		model.addAttribute("blogId", blogId);
		return "blog/admin-write";
	}
	
	@RequestMapping(value="/admin/write", method=RequestMethod.POST)
	public String write(@PathVariable("id") String blogId, PostVo postVo) {
		
		postService.addPost(postVo);
		
		return "redirect:/" + blogId;
	}
}