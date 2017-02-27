<%--
  User: mingfei
  Date: 2/24/17
  Time: 16:17
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>词性</title>
</head>
<body>
<h1>添加词性</h1>
<form action="pos" method="post">
    <input type="hidden" name="action" value="add">
    <input type="hidden" name="wordId" value="${sessionScope.wordId}">
    <input type="text" name="pos"><br>
    <input type="submit" value="添加">
</form>
<hr>
<table border="1" style="border-collapse: collapse">
    <caption>词性列表</caption>
    <tr>
        <th>序号</th>
        <th>词性</th>
        <th colspan="5">操作</th>
    </tr>
    <c:forEach var="pos" items="${sessionScope.poss}" varStatus="vs">
        <tr>
            <td>${vs.count}</td>
            <td>${pos.pos}</td>
            <td><a href="pos?action=search&id=${pos.id}">编辑</a></td>
            <td><a href="pos?action=remove&id=${pos.id}">删除</a></td>
            <td><a href="concise?action=queryByPosId&posId=${pos.id}">添加简明释义</a></td>
            <td><a href="detail?action=queryByPosId&posId=${pos.id}">添加详细释义</a></td>
            <td><a href="sentence?action=add&posId=${pos.id}">添加例句</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
