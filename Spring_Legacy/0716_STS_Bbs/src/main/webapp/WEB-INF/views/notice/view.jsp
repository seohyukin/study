<%@page import="mybatis.vo.CommVO"%>
<%@page import="mybatis.vo.BbsVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//request에 vo라는 이름으로 저장된 객체를 얻어내자.
	Object obj = request.getAttribute("vo");
	
	if (obj != null) {
		BbsVO vo = (BbsVO)obj;
%>
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
					<td><%=vo.getSubject() %></td>
				</tr>

				<tr>
					<th>첨부파일:</th>
					<td>
					<%
						if(vo.getFile_name() != null) {
					%>
						<a href="javascript:down('<%=vo.getFile_name() %>')">
							<%=vo.getFile_name() %>
						</a>
					<%
						}
					%>
					</td>
				</tr>
				
				<tr>
					<th>이름:</th>
					<td><%=vo.getWriter() %></td>
				</tr>
				<tr>
					<th>내용:</th>
					<td><%=vo.getContent() %></td>
				</tr>
				
				<tr>
					<td colspan="2">
						<input type="button" value="수정"
						onclick="javascript:location.href='Controller?type=edit&b_idx=<%=vo.getB_idx()%>&bname=${param.bname}&cPage=${param.cPage}'"/>
						<input type="button" value="삭제"
						onclick="delBbs()"/>
						<input type="button" value="목록" 
						onclick="listBbs()"/>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
	<form method="post" action="Controller">
		<input type="hidden" name="type" value="answer"/>
		이름:<input type="text" name="writer"/><br/>
		내용:<textarea rows="4" cols="55" name="comm"></textarea><br/>
		비밀번호:<input type="password" name="pwd"/><br/>
		
		<%-- 원글의 기본키 --%>
		<input type="hidden" name="bname" value="${param.bname}"/>
		<input type="hidden" name="b_idx" value="${param.b_idx}">
		<input type="hidden" name="cPage" value="${param.cPage}"/>
		<input type="submit" value="저장하기" onclick="addComment()"/> 
	</form>
	
	댓글들<hr/>
	<%
		for (CommVO cvo : vo.getC_list()) {
	%>
		<div class="list_bg">
			<div class="list_item">
				이름: <%=cvo.getWriter() %> &nbsp;&nbsp;
				날짜: <%=cvo.getWrite_date() %><br/>
				내용: <%=cvo.getContent() %>
			</div>
		</div>
	<%
		}
	%>
	
	</div>
	
	<form name="frm" action="Controller" method="post">
		<input type="hidden" name="type" value="del"/>
		<input type="hidden" name="fname"/>
		<input type="hidden" name="bname" value="${param.bname}"/>
		<input type="hidden" name="b_idx" value="${param.b_idx}"/>
		<input type="hidden" name="cPage" value="${param.cPage}"/>
	</form>
	
	<script>
		function listBbs() {
			//get 방식 (url에 다 뜸)
			//location.href="Controller?type=list&?type=list&cPage=${param.cPage}&bname=${param.bname}"
					
			//post 방식 (본문에 form 만들기)
			document.frm.type.value = "list";
			document.frm.submit();
		}
		
		function delBbs() {
			let res = confirm("삭제하시겠습니까?");
			if(res){
				document.frm.type.value = "del";
				document.frm.submit();
			}
		}
		
		function down(fname) {
			//인자로 사용자가 클릭한 파일명을 받는다.
			//이것을 현재 문서 안에 있는 frm이라는 폼 객체에 이름이 fname인 hidden요소의 값(value)으로 지정하자.
			document.frm.fname.value = fname;
			document.frm.type.value = "down";
			document.frm.submit();
		}
		
		function addComment() {
			document.frm.type.value = "answer";
			document.frm.submit();
		}
	</script>
</body>
</html>
<%
	} else {
		response.sendRedirect("Controller"); 
	}
%>












