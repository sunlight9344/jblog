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
</head>

<script>
	function checkUserIdExist(){
		var user_id = $("#blogId").val()
		
		if(blogId.length == 0 ){
			alert('아이디를 입력해주세요')
			return
		}
		$.ajax({
			url : '${pageContext.request.contextPath }user/checkUserIdExist/' + blogId,
			type : 'get',
			dataType : 'text',
			success : function(result){
				if(result.trim()=='true'){
					alert('사용할 수 있는 아이디입니다.')
					$("#userIdExist").val('true')
				}
				else{
					alert('사용할 수 없는 아이디입니다.')
					$("#userIdExist").val('false')
				}
			}
		})
	}
</script>

<body>
	<div class="center-content">
		<h1 class="logo">JBlog</h1>
		<c:import url ="/WEB-INF/views/includes/menu.jsp" />

		<form:form modelAttribute="userVo" class="join-form" id="join-form" name="join-form"
			method="post" action="${pageContext.request.contextPath }/user/join">

			<label class="block-label" for="name">이름</label>
			<form:input path="name" />
			<p style="padding: 3px 0 5px 0; text-align: left; color: #f00">
				<form:errors path="name" />
			</p>

			<label class="block-label" for="blog-id">아이디</label>
			<form:input path="id" />
			<input id="btn-checkemail" type="button" value="id 중복체크" onclick="checkUserIdExist()">
			<input type="hidden" id="idcheck" name="idcheck" value=${idcheck }>
			<p style="padding: 3px 0 5px 0; text-align: left; color: #f00">
				<form:errors path="id" />
			</p>
			
			<img id="img-checkemail" style="display: none;"
				src="${pageContext.request.contextPath}/assets/images/check.png">

			<label class="block-label" for="password">패스워드</label>
			<form:input path="password" />
			<p style="padding:3px 0 5px 0; text-align: left; color: #f00">
				<form:errors path="password" />
			</p>

			<fieldset>
				<legend>약관동의</legend>
				<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
				<label class="l-float">서비스 약관에 동의합니다.</label>
			</fieldset>

			<input type="submit" value="가입하기">
		</form:form>
		
	</div>
</body>
</html>
