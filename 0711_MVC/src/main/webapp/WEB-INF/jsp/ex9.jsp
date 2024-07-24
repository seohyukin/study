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
	<h2>코드: ${c_idx }</h2>
	<h2>사용자: ${u_name }</h2>
	<h2>분류: ${unit }</h2>
	<h2>취미: </h2>
	<ol>
		<c:forEach var="str" items="${hobby }">
			<li>${str }</li>
		</c:forEach>
	</ol>
</body>
</html>