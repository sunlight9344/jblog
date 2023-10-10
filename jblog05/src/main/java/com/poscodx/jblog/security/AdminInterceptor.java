package com.poscodx.jblog.security;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.poscodx.jblog.service.BlogService;
import com.poscodx.jblog.service.CategoryService;
import com.poscodx.jblog.service.PostService;
import com.poscodx.jblog.service.UserService;
import com.poscodx.jblog.vo.BlogVo;
import com.poscodx.jblog.vo.CategoryVo;
import com.poscodx.jblog.vo.UserVo;

public class AdminInterceptor implements HandlerInterceptor {

	@Autowired
	BlogService blogService;
	@Autowired
	PostService postService;
	@Autowired
	CategoryService categoryService;
	@Autowired
	UserService userService;
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		System.out.println("AdminInterceptor Called ...");
		
		//1. handler 종류 확인
		if(!(handler instanceof HandlerMethod)) {
			return true;
		}
		
		//2. 로그인 여부 확인
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		if(authUser == null) {
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false;
		}
		
		//3. AuthUser 과 UserVo blogId 비교
		 String requestURI = request.getRequestURI();
	        String contextPath = request.getContextPath();
	        String regex = "^" + Pattern.quote(contextPath) + "/([^/]+)/admin/([^/]+)";
	        Pattern pattern = Pattern.compile(regex);
	        Matcher matcher = pattern.matcher(requestURI);

	        if (matcher.matches()) {
	            // 주소에서 추출한 아이디 값을 가져옴
	            String blogId = matcher.group(1);
	            // 이제 blogId 변수에 주소에서 추출한 아이디 값이 저장되어 있음
	            System.out.println(blogId);
	            if(!blogId.equals(authUser.getId())) {
	            	response.sendRedirect(request.getContextPath() + "/" + blogId);
	    			return false;
	            }
	        }
	        
	        if ("POST".equals(request.getMethod()) && request.getRequestURI().endsWith("/admin/category")) {
				if(request.getParameter("name").equals("")) {
					UserVo userVo = userService.getUser(authUser.getId());
					BlogVo blogVo = blogService.findById(authUser.getId());
					List<CategoryVo> list = categoryService.getAllContents(authUser.getId());
					List<Integer> countList = new ArrayList<>();
					for(CategoryVo vo : list) {
						countList.add(postService.count(vo.getNo()));
					}
					request.setAttribute("userVo", userVo);
					request.setAttribute("vo", blogVo);
					request.setAttribute("list", list);
					request.setAttribute("countList", countList);
					request.setAttribute("errorName", "카테고리명을 입력해주세요.");
					request.setAttribute("blogId", authUser.getId());
					request
						.getRequestDispatcher("/WEB-INF/views/blog/admin-category.jsp")
						.forward(request, response);
					return false;
				}
	        }
	        
	    if ("POST".equals(request.getMethod()) && request.getRequestURI().endsWith("/admin/write")) {
	    	if(request.getParameter("title").equals("")) {
	    		
	    		UserVo userVo = userService.getUser(authUser.getId());
	    		BlogVo blogVo = blogService.findById(authUser.getId());
	    		List<CategoryVo> list = categoryService.getAllContents(authUser.getId());
	    		
	    		request.setAttribute("userVo", userVo);
	    		request.setAttribute("vo", blogVo);
	    		request.setAttribute("list", list);
	    		request.setAttribute("blogId", authUser.getId());
	    		request.setAttribute("errorTitle", "제목을 입력해주세요.");
	    		
				request
					.getRequestDispatcher("/WEB-INF/views/blog/admin-write.jsp")
					.forward(request, response);
				return false;
	    	}
	    }
	        
		return true;
	}
}
