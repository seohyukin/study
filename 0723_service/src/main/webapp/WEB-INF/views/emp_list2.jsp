<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
		</form>
	</div>
	<br/>
	<table id="t1">
		<caption>사원목록테이블</caption>
		<thead>
			<tr>
				<th>사번</th>
				<th>사원명</th>
				<th>직종</th>
				<th>입사일</th>
				<th>부서코드</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="evo" items="${e_ar }">
			<tr data-sal="${evo.sal }" data-mgr="${evo.mgr }">
				<td>${evo.empno }</td>
				<td>${evo.ename}</td>
				<td>${evo.job }</td>
				<td>${evo.hiredate }</td>
				<td>${evo.deptno }</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<!-- 모달 -->
    <div class="modal_bg" onClick="javascript:popClose();"></div>
	<div class="modal_wrap">
		<header>
	   		<h2>사원 정보</h2>
		</header>
		<table class="table">
			<colgroup>
				<col width="100px"/>
				<col width="*"/>
			</colgroup>
			<thead>
				<tr>
					<th colspan="2">사원정보</th>
                </tr>
            </thead>
            <tbody></tbody>
        </table>
		<button class="modal_close" onClick="javascript:popClose();">닫기</button>
	</div>
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
	
	$("#t1 tbody tr").click(function() {
		viewData(this);
	});
	
	function viewData(tr){
		let th_ar = $("#t1 thead tr").children();
		
		// 클릭한 tr객체가 인자로 넘어온다.
		// 그 tr의 자식들(td들)을 얻어낸다.
		let td_ar = $(tr).children();
		let str = "";
		for(let i=0;i<td_ar.length;i++){
			str += "<tr><th>";
			str += th_ar.eq(i).text();
			str += "</th><td>";
			str += td_ar.eq(i).text();
			str += "</td>";
			str += "</tr>"
		}
		str += "<tr><th>급여</th><td>"
		str += $(tr).data("sal");
		str += "</td></tr>"
		
		str += "<tr><th>부서장 사번</th><td>"
		str += $(tr).data("mgr");
		str += "</td></tr>"
		
		$(".table tbody").html(str);
		popOpen();
	}
	
	function popOpen(){
		var modalPop = $('.modal_wrap');
		var modalBg = $('.modal_bg');
		$(modalPop).show();
		$(modalBg).show();
	}

	function popClose(){
		var modalPop = $('.modal_wrap');
		var modalBg = $('.modal_bg');
		$(modalPop).hide();
		$(modalBg).hide();
	}
</script>
</body>
</html>