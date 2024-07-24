<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	#t1{
		border-collapse: collapse;
		width: 400px;
	}
	
	#t1 caption{
		text-indent: -9999px;
		height: 0;
	}
	#t1 th, #t1 td{
		border: 1px solid black;
		padding: 4px;
	}
	#t1 thead tr{
		background-color: #cdcdcd;
	}
</style>
</head>
<body>
	<h1>부서목록</h1>
	<hr/>
	<div>
		<form action="dept/search" method="post">
			<select name="searchType" id="type">
				<option value="0">부서코드</option>
				<option value="1">부서명</option>
				<option value="2">도시코드</option>
			</select>
			<input type="text" name="searchValue" id="value"/>
			<button type="button" onclick="sendData(this.form)">검색</button>
		</form>
	</div>
	<table id="t1">
		<caption>부서목록테이블</caption>
		<thead>
			<tr>
				<th>부서코드</th>
				<th>부서명</th>
				<th>도시코드</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="vo" items="${d_ar }">
			<tr>
				<td>${vo.deptno }</td>
				<td>${vo.dname }</td>
				<td>${vo.loc_code }</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<script>
	function sendData(ff) {
		//유효성 검사
		let t = $("#type").val();
		let v = $("#value").val();
		
		//비동기식 통신
		$.ajax({
			url: "dept/search",
			data: {"searchType":t, "searchValue":v},
			type: "post",
			dataType: "json", //서버에서 보내오는 자원의 타입
		}).done(function(data){
			console.log(data);
			console.log(data.len);
			
			let d_ar = data.d_ar
			console.log(d_ar[0].deptno);

			let str = "";
			for (let i = 0; i < data.len; i++) {
				str += "<tr><td>";
				str += d_ar[i].deptno
				str += "</td>"
				str += "<td>"
				str += d_ar[i].dname
				str += "</td>"
				str += "<td>"
				str += d_ar[i].loc_code
				str += "</td></tr>"
			}
			$("#t1 tbody").html(str);
		});
	}
</script>
</body>
</html>