<%--
  User: mingfei
  Date: 2/27/17
  Time: 14:39
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>简明释义</title>
</head>
<body>
<h1>简明释义</h1>
<form action="concise" method="post">
    <input type="hidden" name="action" value="add">
    <input type="hidden" name="posId" value="${param.posId}">
    <input type="text" name="chinese"><br>
    <input type="submit" value="保存">
</form>
</body>
</html>
