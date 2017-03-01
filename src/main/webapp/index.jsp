<%--
  User: mingfei
  Date: 2/24/17
  Time: 13:50
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="d" uri="http://java.sun.com/jsp/jstl/core" %>
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
<c:if test="${sessionScope.word ne null}">
    <hr>
    <h2>${sessionScope.word.english}</h2>
    <span>英 ${sessionScope.word.phoneticUk}</span>
    <span>美 ${sessionScope.word.phoneticUs}</span>
</c:if>
<c:if test="${sessionScope.poss ne null}">
    <c:forEach var="pos" items="${sessionScope.poss}">
        <p><span style="color: darkgreen; font-weight: bold">${pos.pos}</span> ${pos.concise.chinese}</p>
    </c:forEach>
</c:if>

<hr>
<small><a href="admin.jsp">管理员登录</a></small>
<hr>
<pre>
    A   B
    1 > n
    n > 1
    1 <> 1
    n <> n
</pre>
</body>
</html>
