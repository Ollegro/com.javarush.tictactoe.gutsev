<%--@elvariable id="data" type="InitServletTest"--%>
<%@ page import="com.tictactoe.Sign" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <link href="static/main.css" rel="stylesheet">
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <script src="<c:url value="/static/jquery-3.6.0.min.js"/>"></script>
    <title>КРЕСТИКИ - НОЛИКИ</title>


</head>
<body>
<h1 style="text-align: center">КРЕСТИКИ - НОЛИКИ</h1>

<table >
    <tr>
        <td onclick="window.location='/logic?click=0'">${data.get(0).getSign()}</td>
        <td onclick="window.location='/logic?click=1'">${data.get(1).getSign()}</td>
        <td onclick="window.location='/logic?click=2'">${data.get(2).getSign()}</td>
    </tr>
    <tr>
        <td onclick="window.location='/logic?click=3'">${data.get(3).getSign()}</td>
        <td onclick="window.location='/logic?click=4'">${data.get(4).getSign()}</td>
        <td onclick="window.location='/logic?click=5'">${data.get(5).getSign()}</td>
    </tr>
    <tr>
        <td onclick="window.location='/logic?click=6'">${data.get(6).getSign()}</td>
        <td onclick="window.location='/logic?click=7'">${data.get(7).getSign()}</td>
        <td onclick="window.location='/logic?click=8'">${data.get(8).getSign()}</td>
    </tr>
</table>


<c:set var="CROSSES" value="<%=Sign.CROSS%>"/>
<c:set var="NOUGHTS" value="<%=Sign.NOUGHT%>"/>

<%--@elvariable id="winner" type="InitServletTest"--%>
<c:if test="${winner == CROSSES}">
    <h1 style="text-align: center">Крестики выйграли!</h1>
    <button onclick="restart()">Начать сначала</button>
</c:if>
<c:if test="${winner == NOUGHTS}">
    <h1 style="text-align: center">Нолики выйграли!</h1>
    <button onclick="restart()">Начать сначала</button>
</c:if>
<%--@elvariable id="draw" type="InitServletTest"--%>
<c:if test="${draw}">
    <h1 style="text-align: center">НИЧЬЯ!</h1>
    <button onclick="restart()">Начать сначала</button>
</c:if>

<script>
    function restart() {
        $.ajax({
            url: '/restart',
            type: 'POST',
            contentType: 'application/json;charset=UTF-8',
            async: false,
            success: function () {
                location.reload();
            }
        });
    }


</script>


</body>
</html>