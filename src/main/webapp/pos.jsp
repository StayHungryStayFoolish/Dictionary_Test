<%--
  User: mingfei
  Date: 2/24/17
  Time: 16:17
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>词性</title>
</head>
<body>
<h1>添加词性</h1>
<form action="pos" method="post">
    <input type="hidden" name="action" value="add">
    <input type="hidden" name="wordId" value="${param.wordId}">
    <input type="text" name="pos"><br>
    <input type="submit" value="添加">
</form>
</body>
</html>
