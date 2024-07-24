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
	#t1 th{
		padding: 6px;
		background-color: #dedede;
		border: 1px solid black;
	}
	#t1 td{
		padding: 4px;
		border: 1px solid black;
	}
	#t1 .no-border{ border: none; }
	#type, #value, #btn1{
		padding: 5px;
	}
	.w150{ width: 150px; }
	
</style>
</head>
<body>
	<h1>회원 목록</h1>
	<hr/>
	<table id="t1">
		<caption>회원목록테이블</caption>
		<thead>
			<tr>
				<td colspan="3" class="no-border">
					<form action="test/search" method="post">
						<select id="type">
							<option value="0">이름</option>
							<option value="1">이메일</option>
							<option value="2">연락처</option>
						</select>
						<input type="text" id="value" class="w150"/>
						<button type="button" onclick="sendData(this.form)" id="btn1">
							검색
						</button>
					</form>
				</td>
			</tr>
			<tr>
				<th>회원명</th>
				<th>이메일</th>
				<th>전화번호</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${m_ar }" var="mvo">
			<tr>
				<td>${mvo.name }</td>
				<td>${mvo.email }</td>
				<td>${mvo.phone }</td>
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
			url: "test/search",
			data: {"searchType":t, "searchValue":v},
			type: "post",
			dataType: "json", //서버에서 보내오는 자원의 타입
		}).done(function(data){
			console.log(data);
			
			let m_ar = data.m_ar
 			console.log(m_ar[0].name);
 			console.log(m_ar[0].email);
 			console.log(m_ar[0].phone);
 			
			let str = "";
			for (let i = 0; i < m_ar.length; i++) {
				if (m_ar[i].name != null) {
					str += "<tr><td>";
					str += m_ar[i].name;
					str += "</td>";
					str += "<td>";
					str += m_ar[i].email;
					str += "</td>";
					str += "<td>";
					str += m_ar[i].phone;
					str += "</td></tr>";
				}
			}
			$("#t1 tbody").html(str);
		});
	}
	
</script>
</body>
</html>







