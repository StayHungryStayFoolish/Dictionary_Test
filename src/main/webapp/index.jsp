<%--
  User: mingfei
  Date: 2/24/17
  Time: 13:50
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>在线词典</title>
</head>
<body>
<h1>在线词典</h1>
<form action="word" method="post">
    <input type="hidden" name="action" value="queryByEnglish">
    <input type="text" name="english" placeholder="请输入英文单词">
    <input type="submit" value="查词">
</form>
<hr>
<small><a href="admin.jsp">管理员登录</a></small>
</body>
</html>
