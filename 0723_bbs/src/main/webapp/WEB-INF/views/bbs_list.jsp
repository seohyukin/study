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
	#t1 th:nth-child(4), #t1 td:nth-child(4) {
   		width: 40%;
	}
</style>
</head>
<body>
	<h1>게시판목록</h1>
	<button type="button" onclick="toMemo()">메모장</button>
	<hr/>
	<table id="t1">
		<caption>게시판</caption>
		<thead>
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>글쓴이</th>
				<th>내용</th>
				<th>날짜</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="bvo" items="${b_ar }">
			<tr>
				<td>${bvo.b_idx }</td>
				<td>${bvo.subject}</td>
				<td>${bvo.writer }</td>
				<td>${bvo.content }</td>
				<td>${bvo.write_date }</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>

<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<script>
	function toMemo() {
		location.href = "http://localhost:8080/app/memo";
	}
</script>
</body>
</html>