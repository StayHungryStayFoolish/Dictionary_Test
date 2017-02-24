<%--
  User: mingfei
  Date: 2/24/17
  Time: 13:50
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>管理单词</title>
</head>
<body>
<c:if test="${sessionScope.username eq null}">
    <c:redirect url="admin"/>
</c:if>
<h1>管理单词</h1>
管理员：${sessionScope.username}
<hr>
<form action="word" method="post">
    <input type="hidden" name="action" value="add">
    <input type="text" name="english" placeholder="ENGLISH"><br>
    <input type="text" name="phoneticUk" placeholder="PHONETIC UK"><br>
    <input type="text" name="phoneticUs" placeholder="PHONETIC US"><br>
    <input type="submit" value="保存">
</form>
<table border="1" style="border-collapse: collapse">
    <caption>单词表</caption>
    <tr>
        <th>序号</th>
        <th>英文</th>
        <th>音标 英</th>
        <th>音标 美</th>
        <th colspan="2">操作</th>
    </tr>
    <c:forEach var="word" items="${sessionScope.words}" varStatus="vs">
        <tr>
            <td>${vs.count}</td>
            <td>${word.english}</td>
            <td>${word.phoneticUk}</td>
            <td>${word.phoneticUs}</td>
            <td><a href="word?action=search&id=${word.id}">编辑</a></td>
            <td><a href="word?action=remove&id=${word.id}">删除</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
