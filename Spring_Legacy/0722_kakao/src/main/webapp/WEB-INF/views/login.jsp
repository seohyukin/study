<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<header>
		<h1>로그인</h1>
	</header>
	<!-- 세션에 mvo라는 값이 없을 때 로그인 화면이 나타나야 한다. -->
	<c:if test="${sessionScope.mvo eq null }">
		<form action="" method="post">
			ID: <input type="text" name="mid"/><br/>
			PW: <input type="text" name="mpw"/><br/>
			<input type="button" value="LOGIN"/>
		</form>
		<br/>
		
		<a href="https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=672f558f632f4de78d77c876656bd3b5&redirect_uri=http://localhost:8080/login/kakao">
			<img src="resources/images/kakao_login.png"/>
		</a>
	</c:if>
</body>
</html>

