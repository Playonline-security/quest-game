<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>El bosque del eco</title>
    <script src="<c:url value='/js/sounds.js'/>"></script>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
<div class="container">

    <div class="video-container">
        <video controls style="width: 100%; border-radius: 10px;">
            <source src="<c:url value='/videos/intro.mp4'/>" type="video/mp4">
            Tu navegador no soporta este video.
        </video>
    </div>

    <c:choose>
        <c:when test="${empty sessionScope.playerName}">
            <h1>El bosque del eco</h1>
            <p>Esta es una aventura narrativa donde cada elección que tomes forjará tu camino. Lee con atención y decide tu destino.</p>
            <p>Eres un viajero valiente. Antes de entrar, dinos tu nombre:</p>

            <form action="${pageContext.request.contextPath}/game" method="POST" class="name-form">
                <label for="playerName">Nombre del personaje</label>
                <input type="text" id="playerName" name="playerName" class="name-input" required>
                <button type="submit" class="btn">Entrar al bosque</button>
            </form>
        </c:when>

        <c:otherwise>
            <h1>¡Bienvenido de nuevo, <c:out value='${sessionScope.playerName}'/>!</h1>
            <p>El bosque del eco te espera. ¿Te atreves a entrar otra vez?</p>
            <div class="choices">
                <a href="${pageContext.request.contextPath}/game?restart=true" class="btn">Entrar al bosque</a>
                <a href="${pageContext.request.contextPath}/game?action=changeName" class="btn-secondary">Cambiar nombre</a>
            </div>
        </c:otherwise>
    </c:choose>

</div>

<!-- Sincronización localStorage - sesión JSP -->
<script>
document.addEventListener('DOMContentLoaded', () => {
    const nameFromServer = "${sessionScope.playerName != null ? sessionScope.playerName : ''}";

    if (nameFromServer && nameFromServer.trim() !== "") {
        // Guardar nombre en localStorage si no existe o está desactualizado
        if (!localStorage.getItem('playerName') || localStorage.getItem('playerName') !== nameFromServer) {
            localStorage.setItem('playerName', nameFromServer);
            console.log("Nombre sincronizado con localStorage:", nameFromServer);
        }
    } else {
        // Si no hay sesión, limpiar localStorage
        localStorage.removeItem('playerName');
    }

    // Guardar el nombre antes de enviar el formulario
    const form = document.querySelector('.name-form');
    if (form) {
        form.addEventListener('submit', () => {
            const input = document.getElementById('playerName');
            const name = input.value.trim();
            if (name) {
                localStorage.setItem('playerName', name);
            }
        });
    }

    // Interceptar 'cambiar nombre' para limpiar localStorage antes de redirigir
        const changeNameLink = document.querySelector('a[href*="action=changeName"]');
        if (changeNameLink) {
            changeNameLink.addEventListener('click', () => {
                localStorage.removeItem('playerName');
            });
        }
});
</script>
</body>
</html>
