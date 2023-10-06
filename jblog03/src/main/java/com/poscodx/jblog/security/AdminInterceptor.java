package com.poscodx.jblog.security;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.poscodx.jblog.vo.UserVo;

public class AdminInterceptor implements HandlerInterceptor {

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
	        String regex = "^" + Pattern.quote(contextPath) + "/([^/]+)/admin/basic";
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
		return true;
	}
}
