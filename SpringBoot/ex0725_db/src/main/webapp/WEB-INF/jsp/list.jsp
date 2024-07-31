<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

</head>
<body>
    <header>
        <h2>목록</h2>
    </header>
    <div>
        <ul>
            <c:forEach var="evo" items="${e_ar}"> <!-- begin="1" end="10" 속성 넣어줄 수 있음 -->
                <li>${evo.empno}, ${evo.ename}, ${evo.job}, ${evo.deptno}</li>
            </c:forEach>
        </ul>
    </div>
</body>
</html>
