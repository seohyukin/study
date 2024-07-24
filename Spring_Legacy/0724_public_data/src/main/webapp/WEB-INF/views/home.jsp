<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	<c:forEach var="dvo" items="${d_list }">
		<p>일산화탄소: ${dvo.coValue }, 등급: ${dvo.coGrade }</p>
		<p>미세먼지: ${dvo.pm10Value }, 등급: ${dvo.pm10Grade }</p>
		<p>통합대기: ${dvo.khaiValue }, 등급: ${dvo.khaiGrade }</p>
		<p>${dvo.dataTime }</p>
	</c:forEach>  
</h1>

</body>
</html>
