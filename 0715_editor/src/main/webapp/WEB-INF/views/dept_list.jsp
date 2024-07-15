<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	#box table{
		width: 550px;
		margin-left: 10px;
		border: 1px solid black;
		border-collapse: collapse;
		font-size: 13px;
	}
	#box table th, #box table td{
		text-align: center;
		border: 1px solid black;
		padding: 4px;
	}
</style>
</head>
<body>
	<div id="box">
		<header>
			<h1>부서목록</h1>
		</header>
		<table>
			<caption>부서목록</caption>
			<thead>
				<tr>
					<td colspan="3">
						<form action="dept_search" method="post">
							<select name="searchType">
								<option value="1">부서번호</option>
								<option value="2">부서명</option>
								<option value="3">도시코드</option>
							</select>
							<input type="text" name="searchValue"/>
							<button type="button" onclick="exe()">검색</button>
						</form>
					</td>
				</tr>
				<tr>
					<th>부서번호</th>
					<th>부서명</th>
					<th>도시코드</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="dvo" items="${d_ar }">
				<tr>
					<td>${dvo.deptno }</td>
					<td>${dvo.dname }</td>
					<td>${dvo.loc_code }</td>
			</c:forEach>
			</tbody>
		</table>
	</div>
<script>
	function exe() {
		//검색단어(searchValue)를 입력했는지 검증
		let v1 = document.forms[0].searchValue.value;
		if (v1.trim().length < 1) {
			alert("검색단어를 입력하세요");
			document.forms[0].searchValue.focus();
			return;
		}
		document.forms[0].submit(); //서버로 전송
	}
</script>
</body>
</html>