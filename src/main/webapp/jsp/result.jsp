<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Tu destino</title>
    <script src="<c:url value='/js/sounds.js'/>"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <div class="container">

        <div class="image-container">
            <img src="<c:url value='/${requestScope.imageUrl}'/>" alt="Resultado del juego">
        </div>

        <h1>Tu destino</h1>
        <p>${requestScope.message}</p>

        <p style="color: #c8b6ff;">Terminaste esta partida en ${requestScope.gameDuration} segundos.</p>

        <div class="choices">
            <a href="${pageContext.request.contextPath}/index.jsp?t=${System.currentTimeMillis()}"
               class="btn"
               onclick="window.top.document.getElementById('game-frame').src = this.href; return false;">
               Jugar de nuevo
            </a>
        </div>

        <%-- Estadísticas de sesión añadidas al final --%>
        <div class="stats">
            <p>
                Estadísticas de ${sessionScope.playerName} <br>
                Partidas: ${sessionScope.gamesPlayed} |
                Victorias: ${sessionScope.gamesWon} |
                Derrotas: ${sessionScope.gamesLost}
            </p>
        </div>
    </div>
</body>
</html>