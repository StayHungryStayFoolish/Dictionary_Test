<%--
  User: mingfei
  Date: 2/24/17
  Time: 13:50
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>管理员登录</title>
</head>
<body>
<h1>管理员登录</h1>
<form action="admin" method="post">
    <input type="hidden" name="action" value="login">
    <input type="text" name="username" placeholder="USERNAME" value="admin"><br>
    <input type="password" name="password" placeholder="PASSWORD" value="123"><br>
    <input type="submit" value="登录">
</form>
${requestScope.message}
</body>
</html>
