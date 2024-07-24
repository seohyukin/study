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
	<!-- jstl은 getter, setter를 자동으로 호출해서 안써줘도 되지만 써줘도 상관없다. -->
	<h2>코드: ${vo.getC_idx() }</h2> 
	<h2>사용자: ${vo.u_name }</h2>
	<h2>분류: ${vo.unit }</h2>
	<h2>취미: </h2>
	<ol>
		<c:forEach var="str" items="${vo.hobby }">
			<li>${str }</li>
		</c:forEach>
	</ol>
</body>
</html>