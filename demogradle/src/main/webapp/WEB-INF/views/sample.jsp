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
<h1>스프링 그래들 부트 시작했습니다</h1>
<c:forEach begin="1" end="10" var="i">${i }<br></c:forEach>
</body>
</html>