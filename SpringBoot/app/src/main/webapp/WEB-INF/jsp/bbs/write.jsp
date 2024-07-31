<%@page import="com.sist.app.vo.BbsVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="resources/css/summernote-lite.css"/>
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
</head>
<body>
	<div id="bbs">
		<form action="write" method="post" encType="multipart/form-data">
		<!-- 폼에 파일을 지정하게 될 때 encType을 반드시 multipart로 지정 -->
			<input type="hidden" name="bname" value="${param.bname}"/>
			<table summary="게시판 글쓰기">
				<caption>게시판 글쓰기</caption>
				<tbody>
					<tr>
						<th>제목:</th>
						<td><input type="text" name="subject" size="45"/></td>
					</tr>
					<tr>
						<th>이름:</th>
						<td><input type="text" name="writer" size="12"/></td>
					</tr>
					<tr>
						<th>내용:</th>
						<td><textarea name="content" cols="50" id="content" rows="8"></textarea></td>
					</tr>
					<tr>
						<th>첨부파일:</th>
						<td><input type="file" name="file"/></td>
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
							<input type="button" value="목록" onclick="javascript:location.href='list?bname=${param.bname}&cPage=${param.cPage}'"/>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<script src="resources/js/summernote-lite.js"></script>
<script>
	$(function(){
		$("#content").summernote({
			width: 500,
			height: 250,
			maxHeight: 400,
			minHeight: 150,
			
			callbacks:{
				onImageUpload: function(files, editor) {
					//이미지가 추가될 때마다 실행되는 곳
					//이것을 서버로 비동기식 통신을 수행하면 서버에 업로드 시킬 수 있음
					for (let i = 0; i < files.length; i++) {
						sendImage(files[i], editor);
					}
				}
			}
		});
	});
	
	function sendImage(file, editor) {
		//파일을 첨부해서 비동기식으로 보낼 때 FormData를 활용하는 것
		let ff = new FormData();
		
		//전송하고자 하는 이미지 파일을 파라미터로 설정
		ff.append("s_file", file);
		
		//비동기식 통신 (파일을 첨부할 때는 contentType, processData, cache를 false로 바꾸자)
		$.ajax({
			url: "saveImg",
			data: ff,
			//data를 json형태로 보낼 때의 기본형식 (파일은 문자열이 아니므로 "filename":"test.txt"같이 보낼 수는 없어서 폼데이터로 꼭 담아서 보내야함)
			//{
			// "size":"3",
			// "ar":[{"name":"홍길동", "phone":"010"}, 
			// 		{"name":"홍길동", "phone":"010"}, 
			// 		{"name":"홍길동", "phone":"010"}]
			//}
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

