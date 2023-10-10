<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery/jquery-1.9.0.js"></script>
</head>
<body>
	<div class="center-content">
		<h1 class="logo">JBlog</h1>
		<c:import url ="/WEB-INF/views/includes/menu.jsp" />
		<form class="login-form" method="post" action="${pageContext.request.contextPath }/user/auth">
      		<label>아이디</label> 
      		<input type="text" name="id" style="height:15px" value=${id }>
      		<p style="text-align: left; color: #f00">
				${errorId }
			</p>
      		<br>
      		<label>패스워드</label> 
      		<input type="password" name="password" style="height:15px">
			<p style="text-align: left; color: #f00">
				${errorPassword }</p>
			<br>
      		<input type="submit" value="로그인">
		</form>
	</div>
</body>
</html>
