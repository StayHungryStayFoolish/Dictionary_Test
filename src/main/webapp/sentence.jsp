<%--
  User: mingfei
  Date: 2/27/17
  Time: 14:39
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>例句</title>
</head>
<body>
<h1>例句</h1>
<form action="sentence" method="post">
    <input type="hidden" name="action" value="add">
    <input type="hidden" name="posId" value="${sessionScope.posId}">
    <input type="text" name="english"><br>
    <input type="text" name="chinese"><br>
    <input type="submit" value="保存">
</form>
<table border="1" style="border-collapse: collapse;">
    <caption>例句列表</caption>
    <tr>
        <th>序号</th>
        <th>英文</th>
        <th>中文</th>
        <th colspan="2">操作</th>
    </tr>
    <c:forEach var="sentence" items="${sessionScope.sentences}" varStatus="vs">
        <tr>
            <td>${vs.count}</td>
            <td>${sentence.english}</td>
            <td>${sentence.chinese}</td>
            <td><a href="sentence?action=search&id=${sentence.id}">编辑</a></td>
            <td><a href="sentence?action=remove&id=${sentence.id}">删除</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
