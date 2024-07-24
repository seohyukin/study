<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	#t1 {
	    border-collapse: collapse;
	    width: 50%;
	    table-layout: fixed;
	}
	#t1 th, #t1 td {
	    border: 1px solid black;
	    padding: 8px;
	    text-align: center;
	    word-wrap: break-word;
	}
	#t1 thead tr {
	    background-color: #f2f2f2;
	}
	#t1 tbody tr:nth-child(even) {
	    background-color: #f9f9f9;
	}
	#t1 tbody tr:hover {
	    background-color: #e2e2e2;
	}
	#t1 th:nth-child(2), #t1 td:nth-child(2) {
   		width: 40%;
	}
</style>
</head>
<body>
	<h1>게시판목록</h1>
	<button type="button" onclick="toBbs()">게시판</button>
	<hr/>
	<table id="t1">
		<caption>메모장</caption>
		<thead>
			<tr>
				<th>번호</th>
				<th>내용</th>
				<th>작성자</th>
				<th>ip</th>
				<th>작성일</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="mvo" items="${m_ar }">
			<tr>
				<td>${mvo.m_idx }</td>
				<td>${mvo.content}</td>
				<td>${mvo.writer }</td>
				<td>${mvo.ip }</td>
				<td>${mvo.write_date }</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>

<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<script>
	function toBbs() {
		location.href = "http://localhost:8080/app/bbs";
	}
</script>
</body>
</html>