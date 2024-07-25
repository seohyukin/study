<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
	}
	#t1 td{
		cursor: pointer;
	}
	#t1 thead tr{
		background-color: #cdcdcd;
	}
	.modal_bg {
	display: none;
	width: 100%;
	height: 100%;
	position: fixed; 
	top: 0;
	left: 0;
	right: 0;
	background: rgba(0, 0, 0, 0.6);
	z-index: 999; 
	}
	.modal_wrap {
	display: none;
	position: absolute; 
	top: 50%;
	left: 50%;
	transform:translate(-50%,-50%);
	width: 400px;
	height: 400px;
	background: #fff;
	z-index: 1000; 
	}
	.modal_btn{
		text-align: center;
		padding-top: 20px;
		position: relative;
		bottom: 0;
	}
	.header{
		text-align: center;
	}
	.table{
		border-collapse: collapse;
		width: 380px;
		margin: auto;
	}
	.table th, .table td {
		border: 1px solid black;
		padding: 4px;
	}
	.table thead tr{
		background-color: #cdcdcd;
	}
</style>
</head>
<body>
	<h1>사원목록</h1>
	<hr/>
	<div>
		<form action="emp_search" method="post">
			<select name="searchType" id="type">
				<option value="0">사원번호</option>
				<option value="1">사원명</option>
				<option value="2">직종</option>
				<option value="3">부서코드</option>
			</select>
			<input type="text" name="searchValue" id="value" />
			<button type="button" onclick="sendData(this.form)">검색</button>
			<button type="button" onclick="sendData_ajax(this.form)">검색_ajax</button>
		</form>
	</div>
	<br/>
	<table id="t1">
		<caption>사원목록테이블</caption>
		<thead>
			<tr>
				<th>사원번호</th>
				<th>사원명</th>
				<th>직종</th>
				<th>부서코드</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="evo" items="${e_ar }">
			<tr>
				<td>${evo.empno }</td>
				<td>${evo.ename}</td>
				<td>${evo.job }</td>
				<td>${evo.deptno }</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<script>
    function sendData(frm) {
		// 유효성 검사
		let keyword = $("#value").val().trim();
		if(keyword.length < 1 || keyword == ''){
			alert("검색어를 입력하세요")
			$("#value").val('');
			$("#value").focus();
			return;
		}
		frm.submit();
	}

	function sendData_ajax(frm) {
		// 유효성 검사
		let keyword = $("#value").val().trim();
		if(keyword.length < 1 || keyword == ''){
			alert("검색어를 입력하세요")
			$("#value").val('');
			$("#value").focus();
			return;
		}

		$.ajax({
			url: "emp_search",
			data: {
				"searchType" : $("#type").val(),
				"searchValue" : $("#value").val(),
			},
			type: "post",
			dataType: "json",
		}).done(function(data){
			let e_ar = data.e_ar;
			let str = "";
			
			for(let i=0; i<e_ar.length; i++){
				str += "<tr><td>";
				str += e_ar[i].empno;
				str += "</td>";
				str += "<td>";
				str += e_ar[i].ename;
				str += "</td>";
				str += "<td>";
				str += e_ar[i].job;
				str += "</td>";
				str += "<td>";
				str += e_ar[i].deptno;
				str += "</td>";
				str += "</tr>";				
			}
			console.log(str);
			
			$("#t1 tbody").html(str);
		});
	}
</script>
</body>
</html>