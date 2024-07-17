<%@page import="mybatis.vo.BbsVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/summernote-lite.css"/>
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
</style>
<script type="text/javascript">
	
</script>
</head>
<body>
	<div id="bbs">
	<form action="edit" method="post" encType="multipart/form-data">
	<!-- 폼에 파일을 지정하게 될 때 encType을 반드시 multipart로 지정 -->
		<input type="hidden" name="bname" value="${param.bname }"/>
		<input type="hidden" name="b_idx" value="${vo.file_name }"/>
		<input type="hidden" name="cPage" value="${param.cPage }"/>
		<table summary="게시판 수정">
			<caption>게시판 수정</caption>
			<tbody>
				<tr>
					<th>제목:</th>
					<td><input type="text" name="title" size="45" value="${vo.subject }"/></td>
				</tr>
				<tr>
					<th>이름:</th>
					<td><input type="text" name="writer" size="12" value="${vo.writer }"/></td>
				</tr>
				<tr>
					<th>내용:</th>
					<td><textarea name="content" cols="50" id="content" rows="8">${vo.content }</textarea></td>
				</tr>
				<tr>
					<th>첨부파일:</th>
					<td>
						<input type="file" name="file"/>
						<c:if test="${vo.file_name ne null }">
							(${vo.file_name }) 파일이 첨부된 상태
						</c:if>
					</td>
				</tr>
<!--
				<tr>
					<th>비밀번호:</th>
					<td><input type="password" name="pwd" size="12"/></td>
				</tr>
-->
				<tr>
					<td colspan="2">
						<input type="button" value="보내기" onclick="sendData()"/>
						<input type="button" value="다시"/>
						<input type="button" value="목록" 
						onclick="javascript:location.href='list&cPage=${param.cPage}&bname=${param.bname}'"/>
						<!-- ${param.cPage}: parameter중에 cPage라는 이름을 가진 인자를 가져옴
							(javascript:location.href -> Redirect) -->
					</td>
				</tr>
			</tbody>
		</table>
	</form>
	</div>
	
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<script src="js/summernote-lite.js"></script>
<script src="js/lang/summernote-ko-KR.js"></script>
<script>
	$(function(){
		$("#content").summernote({
			width: 500,
			height: 250,
			maxHeight: 400,
			minHeight: 150,
			
			callbacks:{
				onImageUpload: function(files, editor) {
					for (let i = 0; i < files.length; i++) {
						sendImage(files[i], editor);
					}//for의 끝
				}
			}
		});
	});
	
	function sendImage(file, editor) {
		let ff = new FormData();
		
		//전송하고자 하는 이미지 파일을 파라미터로 설정
		ff.append("s_file", file);
		
		//비동기식 통신 (파일을 첨부할 때는 contentType, processData, cache를 false로 바꾸자)
		$.ajax({
			url: "saveImg",
			data: ff,
			type: "post",
			contentType: false,
			processData: false,
			cache: false,
			dataType: "json", //서버에서 보내오는 자원의 타입
		}).done(function(data){
			console.log(data);
			$("#content").summernote(
				"editor.insertImage",
				data.url+"/"+data.fname
			);
		});
	}
	
	function sendData(){
		for(var i=0 ; i<document.forms[0].elements.length ; i++){
			if (i > 1)
				break; //에디터에 있는 컴포넌트들에 대한 유효성 검사를 피하기 위해 탈출
			if(document.forms[0].elements[i].value == ""){
				alert(document.forms[0].elements[i].name+
						"를 입력하세요");
				document.forms[0].elements[i].focus();
				return;//수행 중단
			}
		}
		document.forms[0].submit();
	}
	
</script>
</body>
</html>


