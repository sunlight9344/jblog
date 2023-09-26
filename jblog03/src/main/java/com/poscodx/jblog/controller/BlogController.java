package com.poscodx.jblog.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.poscodx.jblog.service.BlogService;
import com.poscodx.jblog.service.FileUploadService;
import com.poscodx.jblog.vo.BlogVo;

@Controller
@RequestMapping(value = "/{id:^(?!assets).*}")
public class BlogController {
	
	@Autowired
	BlogService blogService;
	
	@Autowired
	private FileUploadService fileUploadService;
	
	@RequestMapping({"", "/{categoryNo}", "/{categoryNo}/{postNo}"})
	public String index(
			@PathVariable("id") String blogId,
			@PathVariable("categoryNo") Optional<Long> categoryNo,
			@PathVariable("postNo") Optional<Long> postNo,
			Model model) {
		
		//PathVariable 을 optional 사용해서 null 값을 받을 수 있 또 로로로록
		//asset 이라고 쳤을 때도 들어옴 정적인것도 들어온다 asset 들어오는데 어? blog 아이디인데? 하고 들어온다고 ---> pathVariable 정규 표현식으로 해결 가능 또는 asset 을 그냥 가상 url 로 따두면 되잖아
		//path variable 정규 표현식 assets 으로 시작하지 않는 것으로
		
		BlogVo blogVo = blogService.findById(blogId);
		model.addAttribute("vo", blogVo);
		
		return "blog/main";
	}
	
	@RequestMapping(value="/admin/basic", method=RequestMethod.GET)
	public String adminBasic(@PathVariable("id") String blogId, Model model) {
		
		BlogVo blogVo = blogService.findById(blogId);
		model.addAttribute("vo", blogVo);
		
		return "blog/admin-basic";
	}
	
	@RequestMapping(value="/admin/basic", method=RequestMethod.POST)
	public String adminBasic(BlogVo blogVo, MultipartFile file, @PathVariable("id") String blogId) {
		
		blogVo.setBlogId(blogId);
		String image = fileUploadService.restore(file);
		if(image != null) {
			blogVo.setImage(image);
		}
		blogService.update(blogVo);
		
		return "redirect:/" + blogId;
	}
	
}