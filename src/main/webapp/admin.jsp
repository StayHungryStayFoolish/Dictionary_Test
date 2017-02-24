<%--
  User: mingfei
  Date: 2/24/17
  Time: 13:50
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>管理员</title>
    <script>
        function del() {
            return confirm("是否删除？")
        }
    </script>
</head>
<body>
<c:if test="${sessionScope.username eq null}">
    <h1>管理员登录</h1>
    <form action="admin" method="post">
        <input type="hidden" name="action" value="login">
        <input type="text" name="username" placeholder="USERNAME" value="admin"><br>
        <input type="password" name="password" placeholder="PASSWORD" value="123"><br>
        <input type="submit" value="登录">
    </form>
    ${requestScope.message}
</c:if>
<c:if test="${sessionScope.username ne null}">
    管理员：${sessionScope.username}
    <p><a href="admin?action=logout">注销</a></p>
    <hr>
    <form action="word" method="post">
        <c:if test="${requestScope.word eq null}">
            <input type="hidden" name="action" value="add">
        </c:if>
        <c:if test="${requestScope.word ne null}">
            <input type="hidden" name="action" value="update">
            <input type="hidden" name="id" value="${requestScope.word.id}">
        </c:if>
        <input type="text" name="english" placeholder="ENGLISH" value="${requestScope.word.english}"><br>
        <input type="text" name="phoneticUk" placeholder="PHONETIC UK" value="${requestScope.word.phoneticUk}"><br>
        <input type="text" name="phoneticUs" placeholder="PHONETIC US" value="${requestScope.word.phoneticUs}"><br>
        <input type="submit" value="保存">
    </form>
    <table border="1" style="border-collapse: collapse">
        <caption>单词表</caption>
        <tr>
            <th>序号</th>
            <th>英文</th>
            <th>音标 英</th>
            <th>音标 美</th>
            <th colspan="3">操作</th>
        </tr>
        <c:forEach var="word" items="${sessionScope.words}" varStatus="vs">
            <tr>
                <td>${vs.count}</td>
                <td>${word.english}</td>
                <td>${word.phoneticUk}</td>
                <td>${word.phoneticUs}</td>
                <td><a href="word?action=search&id=${word.id}">编辑</a></td>
                <td><a href="word?action=remove&id=${word.id}" onclick="return del()">删除</a></td>
                <td><a href="pos.jsp?wordId=${word.id}">添加词性</a></td>
            </tr>
        </c:forEach>
    </table>
</c:if>
</body>
</html>
