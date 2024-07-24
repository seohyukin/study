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
	<c:if test="${evo ne null }">
		<table>
			<tbody>
				<tr>
					<th>사번</th>
					<th>이름</th>
					<th>직종</th>
					<th>부서</th>
				</tr>
				<tr>
					<td>${evo.empno}</td>
					<td>${evo.ename}</td>
					<td>${evo.job}</td>
					<td>${evo.deptno}</td>
				</tr>
			</tbody>
		</table>
	</c:if>
	<c:if test="${e_list ne null }">
		<table>
			<tbody>
				<tr>
					<th>사번</th>
					<th>이름</th>
					<th>직종</th>
					<th>부서</th>
				</tr>
				<c:forEach var="e" items="${e_list }">
					<tr>
						<td>${e.empno}</td>
						<td>${e.ename}</td>
						<td>${e.job}</td>
						<td>${e.deptno}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>
</body>
</html>