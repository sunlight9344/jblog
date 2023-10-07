package com.poscodx.jblog.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import com.poscodx.jblog.service.UserService;
import com.poscodx.jblog.vo.UserVo;


public class LoginInterceptor implements HandlerInterceptor {
	
	@Autowired
	private UserService userService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		System.out.println("Login Interceptor called ...");
		
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		
		UserVo authUser = userService.getUser(id, password);
		
		if(id.equals("") && password.equals("")) {
			request.setAttribute("id", id);
			request.setAttribute("errorId", "id를 입력하세요");
			request.setAttribute("errorPassword", "password를 입력하세요");
			request
				.getRequestDispatcher("/WEB-INF/views/user/login.jsp")
				.forward(request, response);
		
			return false;
		}
		
		if(id.equals("")) {
			request.setAttribute("id", id);
			request.setAttribute("errorId", "id를 입력하세요");
			request
				.getRequestDispatcher("/WEB-INF/views/user/login.jsp")
				.forward(request, response);
		
			return false;
		}
		
		if(password.equals("")) {
			request.setAttribute("id", id);
			request.setAttribute("errorPassword", "password를 입력하세요");
			request
				.getRequestDispatcher("/WEB-INF/views/user/login.jsp")
				.forward(request, response);
			
			return false;
		}
		
		if(authUser == null) {
			request.setAttribute("id", id);
			request
				.getRequestDispatcher("/WEB-INF/views/user/login.jsp")
				.forward(request, response);
			
			return false;
		}
		
		System.out.println("auth success------>" + authUser);
		
		HttpSession session = request.getSession(true);
		session.setAttribute("authUser", authUser);
		response.sendRedirect(request.getContextPath());
		
		return false;
	}
}
