package com.poscodx.jblog.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.poscodx.jblog.vo.UserVo;

public class AuthInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		System.out.println("AuthInterceptor called ...");
		
		//1. handler 종류 확인
		if(!(handler instanceof HandlerMethod)) {
			// DefaultServletHandler process (static , /assets/** ...)
			return true;
		}
		
		//2. casting
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		
		//3-1. Handler Method의 @Auth 가져오기
		Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
		
		//3-2. Handler Method의 @Auth가 없을 경우, Type(Class)의 @Auth 가져오기\
		if(auth == null) {
			auth = handlerMethod
					.getMethod()
					.getDeclaringClass()
					.getAnnotation(Auth.class);
		}
		
		//4. @Auth 가 없는 경우
		if(auth == null) {
			return true;
		}
		
		//5. @Auth 가 붙어 있는 경우, 인증(Authenfication) 여부 확인ㅇ
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		if(authUser == null) {
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false;
		}
		
//		//6. 권한(Authorization) 체크를 위해서 @Auth의 Role 가져오기("USER, "ADMIN")
//		String role = auth.Role();
//		String authUserRole = authUser.getRole();
//		
//		if("USER".equals(role)) {
//			return true;
//		}
//		
//		if(!"ADMIN".equals(authUserRole)) {
//			response.sendRedirect(request.getContextPath());
//			return false;
//		}

		//6. 인증 확인!!!
		return true;
	}
}
