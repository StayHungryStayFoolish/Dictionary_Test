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
    <c:forEach var="pos" items="${sessionScope.word.poss}">
        <div><span style="font-weight: bolder; color: darkgreen">${pos.pos}</span> ${pos.concise.chinese}</div>
        <h2>详细释义</h2>
        ${pos.pos}
        <ol>
        <c:forEach var="detail" items="${pos.details}">
            <li>${detail.detail}</li>
        </c:forEach>
        </ol>
    </c:forEach>
    <hr>
    ${sessionScope.word}
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

    test

       参数        表        语句                                      获取
    1. english    word       select * from word where english = ?     id(word)
    2. wordId     pos        select * from pos where wordId = ?       id(pos)
    3. posId      concise    select * from concise where posId = ?
    4. posId      detail     select * from detail where posId = ?
    5. posId      sentence   select * from sentence where posId = ?

    1. word
    2. List<Pos> poss
    3. Concise concise
    4. List<Detail> details
    5. List<Sentence> sentences

       pos.setConcise(concise)
       pos.setDetails(details)
       pos.setSentences(sentences)

       word.setPoss(poss)
       req.getSession().setAttribute("word", word);

</pre>
</body>
</html>
