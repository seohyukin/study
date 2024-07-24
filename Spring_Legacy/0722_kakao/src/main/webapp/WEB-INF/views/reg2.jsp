<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<header>
		<h2>추가정보입력</h2>
	</header>
	<form action="/registry" method="post">
		<img src="${mvo.p_img }" width="100"/><br/>
		이름: <input type="text" name="m_name"/><br/> <!-- vo와 name이 같아야 함 -->
		별칭: <input type="text" name="nickName" readonly="readonly" value="${mvo.nickName }"/><br/>
		이메일: <input type="text" name="email"/><br/>
		연락처: <input type="text" name="phone"/><br/>
		<input type="button" value="저장"/>
	</form>
</body>
</html>