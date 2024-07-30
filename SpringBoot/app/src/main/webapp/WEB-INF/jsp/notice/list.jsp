<%@page import="bbs.util.Paging"%>
<%@page import="mybatis.vo.BbsVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="resources/css/style.css"/>
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
	
	#bbs table th,#bbs table td {
	    text-align:center;
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
	
	/* paging */
	
	table tfoot ol.paging {
	    list-style:none;
	}
	
	table tfoot ol.paging li {
	    float:left;
	    margin-right:8px;
	}
	
	table tfoot ol.paging li a {
	    display:block;
	    padding:3px 7px;
	    border:1px solid #00B3DC;
	    color:#2f313e;
	    font-weight:bold;
	}
	
	table tfoot ol.paging li a:hover {
	    background:#00B3DC;
	    color:white;
	    font-weight:bold;
	}
	
	.disable {
	    padding:3px 7px;
	    border:1px solid silver;
	    color:silver;
	}
	
	.now {
	   padding:3px 7px;
	    border:1px solid #ff4aa5;
	    background:#ff4aa5;
	    color:white;
	    font-weight:bold;
	}
		
</style>
</head>
<body>
<div id="wrap">
	<header>
		<jsp:include page="/WEB-INF/views/menu.jsp"/>
	</header>	
	<div id="bbs">
		<table summary="게시판 목록">
			<caption>게시판 목록</caption>
			<thead>
				<tr class="title">
					<th class="no">번호</th>
					<th class="subject">제목</th>
					<th class="writer">글쓴이</th>
					<th class="reg">날짜</th>
					<th class="hit">조회수</th>
				</tr>
			</thead>
			
			<tfoot>
                      <tr>
                          <td colspan="4">
                              <ol class="paging">
<%
	//페이징을 위해 request에 page라는 이름으로 저장한 객체를 얻어낸다.
	Object obj = request.getAttribute("page");
	Paging pvo = null;
	if (obj != null) {
		pvo = (Paging)obj;
		if (pvo.getStartPage() < pvo.getPagePerBlock()) {
			//startPage는 < 5여도 2,3,4값이 들어가지 않고 1,6,11처럼 들어간다
%>
			<li class="disable">&lt;</li>
<%	
		} else {
%>
			<li>
				<a href="Controller?type=list&bname=bbs&cPage=<%=pvo.getNowPage()-pvo.getPagePerBlock()%>">
					&lt;
				</a>
			</li>
<%
		}
		for(int i = pvo.getStartPage(); i <= pvo.getEndPage(); i++) {
			//현재 페이지 값과 i가 같으면 현재페이지를 의미하는 클래스를 적용하고 그렇지 않으면 링크를 걸어준다.
			if (i == pvo.getNowPage()) {
%>
				<li class="now"><%=i %></li>
<%
			} else {
%>
				<li><a href="Controller?type=list&bname=bbs&cPage=<%=i %>"><%=i %></a></li>
<%
			}
		}
		//다음 블럭으로 이동하는 기능을 부여해야 할지, 하지 말아야 할지를 endPage가 totalPage보다 작을 경우에만 이동할 수 있도록 하자
		if (pvo.getEndPage() < pvo.getTotalPage()) {
%>
			<li>
				<a href="Controller?type=list&bname=bbs&cPage=<%=pvo.getNowPage()+pvo.getPagePerBlock() %>">
					&gt;
				</a>
			</li>
<%
		} else {
%>		
			<li class="disable">&gt;</li>
<% 
		}
	}
%>
	
                              </ol>
                          </td>
						  <td>
							<input type="button" value="글쓰기"
			onclick="javascript:location.href='Controller?type=write&bname=bbs&cPage=<%=pvo.getNowPage()%>'"/>
						  </td>
                      </tr>
                  </tfoot>
			<tbody>
<%
	Object obj2 = request.getAttribute("ar");
	if (obj2 != null) {
		BbsVO[] ar = (BbsVO[])obj2;
		for (BbsVO vo : ar) {
%>
				<tr>
					<td><%=vo.getB_idx() %></td>
					<td style="text-align: left">
						<a href="Controller?type=view&b_idx=<%=vo.getB_idx()%>&bname=bbs
								&cPage=<%=pvo.getNowPage() %>">
							<%=vo.getSubject() %>
							<%
								if (vo.getC_list().size() > 0) {
							%>
								(<%=vo.getC_list().size() %>)
							<%
								}
							%>
						</a>
					</td>
					<td><%=vo.getWriter() %></td>
					<td><%=vo.getWrite_date() %></td>
					<td><%=vo.getHit() %></td>
				</tr>
<%
		}
	} else {
%>
		<tr>
			<td colspan="5">
				등록된 데이터가 없습니다.
			</td>
		</tr>
<%
	}
%>
			</tbody>
		</table>
		
	</div>
</div>
</body>
</html>
