<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>단순JSP페이지</h2>
	<hr/>
	<form action="t7.inc" method="post">
		ID: <input type="text" name="id"/><br/>
		PW: <input type="password" name="pw"/><br/>
		<button type="submit">보내기</button>
	</form>
</body>
</html>