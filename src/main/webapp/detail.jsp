<%--
  User: mingfei
  Date: 2/27/17
  Time: 14:39
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>详细释义</title>
</head>
<body>
<h1>详细释义</h1>
<form action="detail" method="post">
    <input type="hidden" name="action" value="add">
    <input type="hidden" name="posId" value="${sessionScope.posId}">
    <input type="text" name="detail"><br>
    <input type="submit" value="保存">
</form>
<table border="1" style="border-collapse: collapse;">
    <caption>详细释义列表</caption>
    <tr>
        <th>序号</th>
        <th>释义</th>
        <th colspan="2">操作</th>
    </tr>
    <c:forEach var="detail" items="${sessionScope.details}" varStatus="vs">
        <tr>
            <td>${vs.count}</td>
            <td>${detail.detail}</td>
            <td><a href="detail?action=search&id=${detail.id}">编辑</a></td>
            <td><a href="detail?action=remove&id=${detail.id}">删除</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
