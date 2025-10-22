<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Toma una decisión</title>
    <script src="<c:url value='/js/sounds.js'/>"></script>
    <%-- Este enlace es el que carga todo el estilo visual --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <div class="container">

        <%-- Contenedor de la imagen de la escena --%>
        <div class="image-container">
            <img src="<c:url value='/${requestScope.node.imageUrl}'/>" alt="Escena del juego">
        </div>

        <%-- Texto de la escena --%>
        <h2>${requestScope.node.text}</h2>

        <%-- Botones de decisión --%>
        <form action="${pageContext.request.contextPath}/game" method="post" class="choices">
            <c:forEach var="choice" items="${requestScope.node.choices}">
                <button type="submit" name="choice" value="${choice.key}" class="btn">
                    ${choice.value}
                </button>
            </c:forEach>
        </form>

        <%-- Estadísticas --%>
        <div class="stats">
            <p>
                Jugador: ${sessionScope.playerName} |
                Partidas: ${sessionScope.gamesPlayed} |
                Victorias: ${sessionScope.gamesWon} |
                Derrotas: ${sessionScope.gamesLost}
            </p>
        </div>
    </div>
</body>
</html>