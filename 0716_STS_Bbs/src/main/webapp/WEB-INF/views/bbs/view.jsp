<%@page import="mybatis.vo.CommVO"%>
<%@page import="mybatis.vo.BbsVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
	#bbs table { 
	    width:580px;
	    margin-left:10px;
	    border:1px solid black;
	    border-collapse:collapse;
	    font-size:14px;
	    
	}
	
	#bbs table caption {
	    font-size:20px;
	    font-weight:bold;
	    margin-bottom:10px;
	}
	
	#bbs table th {
	    text-align:center;
	    border:1px solid black;
	    padding:4px 10px;
	}
	
	#bbs table td {
	    text-align:left;
	    border:1px solid black;
	    padding:4px 10px;
	}
	
	.no {width:15%}
	.subject {width:30%}
	.writer {width:20%}
	.reg {width:20%}
	.hit {width:15%}
	.title{background:lightsteelblue}
	
	.odd {background:silver}
	
	.list_bg{
		background: rgb(249,250,255);
		padding: 10px;
	}
	.list_item{
		background: #fff;
		border-radius: 10px;
		padding: 20px;
		margin-bottom: 5px;
		box-shadow: rgb(200,200,200) 0px 2px 4px 0px;
	}
</style>

</head>
<body>
	<div id="bbs">
	<form method="post" >
		<table summary="게시판 글쓰기">
			<caption>게시판 글쓰기</caption>
			<tbody>
				<tr>
					<th>제목:</th>
					<td>${vo.subject }</td>
				</tr>

				<tr>
					<th>첨부파일:</th>
					<td>
					<c:if test="${vo.file_name ne null }">
						<a href="javascript:down('${vo.file_name }')">
							${vo.file_name }
						</a>
					</c:if>
					</td>
				</tr>
				
				<tr>
					<th>이름:</th>
					<td>${vo.writer }</td>
				</tr>
				<tr>
					<th>내용:</th>
					<td>${vo.content }</td>
				</tr>
				
				<tr>
					<td colspan="2">
						<input type="button" value="수정" onclick="edit()"/>
						<input type="button" value="삭제" onclick="delBbs()"/>
						<input type="button" value="목록" onclick="listBbs()"/>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
	<form method="post" action="answer">
		<input type="hidden" name="type" value="answer"/>
		이름:<input type="text" name="writer"/><br/>
		내용:<textarea rows="4" cols="55" name="content"></textarea><br/>
		비밀번호:<input type="password" name="pwd"/><br/>
		
		<%-- 원글의 기본키 --%>
		<input type="hidden" name="b_idx" value="${vo.b_idx}">
		<input type="hidden" name="bname" value="${param.bname}"/>
		<input type="hidden" name="cPage" value="${param.cPage}"/>
		<input type="submit" value="저장하기" onclick="addComment()"/> 
	</form>
	
	댓글들<hr/>
	<c:forEach var="cvo" items="${vo.c_list }">
		<div class="list_bg">
			<div class="list_item">
				이름: ${cvo.writer } &nbsp;&nbsp;
				날짜: ${cvo.write_date }<br/>
				내용: ${cvo.content }
			</div>
		</div>
	</c:forEach>
	</div>
	
	<form name="frm" method="post">
		<input type="hidden" name="filename"/>
		<input type="hidden" name="dir" value="upload"/>
		<input type="hidden" name="bname" value="${param.bname}"/>
		<input type="hidden" name="b_idx" value="${param.b_idx}"/>
		<input type="hidden" name="cPage" value="${param.cPage}"/>
	</form>
	
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<script>
	function listBbs() {
		//get 방식 (url에 다 뜸)
		//location.href="Controller?type=list&?type=list&cPage=${param.cPage}&bname=${param.bname}"
				
		//post 방식 (본문에 form 만들기)
		document.frm.action = "list";
		document.frm.submit();
	}
	
	function delBbs() {
		let res = confirm("삭제하시겠습니까?");
		if(res){
			document.frm.action = "del";
			document.frm.submit();
		}
	}
	
	function down(filename) {
		//인자로 사용자가 클릭한 파일명을 받는다.
		//이것을 현재 문서 안에 있는 frm이라는 폼 객체에 이름이 filename인 hidden요소의 값(value)으로 지정하자.
		document.frm.filename.value = filename;
		document.frm.action = "FileDownload";
		document.frm.submit();
	}
	
	function addComment() {
		document.frm.action = "answer";
		document.frm.submit();
	}
	
	function edit() {
		document.frm.action = "edit";
		document.frm.submit();
	}
</script>
</body>
</html>













