<%--
  User: mingfei
  Date: 2/27/17
  Time: 14:39
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<table border="1" style="border-collapse: collapse;">
    <caption>简明释义列表</caption>
    <tr>
        <th>序号</th>
        <th>中文</th>
        <th colspan="2">操作</th>
    </tr>
    <c:forEach var="concise" items="${sessionScope.concises}" varStatus="vs">
        <tr>
            <td>${vs.count}</td>
            <td>${concise.chinese}</td>
            <td><a href="concise?action=search&id=${concise.id}">编辑</a></td>
            <td><a href="concise?action=remove&id=${concise.id}">删除</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
