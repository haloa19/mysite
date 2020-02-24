<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!doctype html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${ pageContext.request.contextPath }/assets/css/user.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="user">
				<c:if test="${not empty kind }"><p>글작성은 로그인이 필요합니다.</p></c:if>
				<form id="login-form" name="loginform" method="post" action="${ pageContext.request.contextPath }/user/login">
					<label class="block-label" for="email">이메일</label>
					<input id="email" name="email" type="text" value="${userVo.email }">
					<label class="block-label" >패스워드</label>
					<input name="password" type="password" value="">
					<input type="submit" value="로그인">
					<c:if test="${not empty userVo }"><p>로그인이 실패 했습니다.</p></c:if>
				</form>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>