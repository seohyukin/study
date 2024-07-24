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
	<c:if test="${mvo ne null }">
		<h2>${mvo.m_name }님 환영</h2>
	</c:if>
	<c:if test="${mvo eq null }">
		<h2>로그인 실패</h2>
	</c:if>
</body>
</html>