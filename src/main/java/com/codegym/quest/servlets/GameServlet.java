package com.codegym.quest.servlets;

import com.codegym.quest.model.GameNode;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@WebServlet(name = "GameServlet", value = "/game")
public class GameServlet extends HttpServlet {

    private final Map<String, GameNode> gameLogic = new LinkedHashMap<>();

    @Override
    public void init() {
        // --- Narrativa: "El bosque del eco" ---

        // --- Nodo inicial ---
        gameLogic.put("start", new GameNode(
                "Estás frente al bosque del eco, del que se dice que nadie regresa. " +
                        "A tu izquierda hay un sendero de setas brillantes. " +
                        "A tu derecha, un camino de piedras oscuras.",
                Map.of("setas", "Seguir las setas brillantes", "piedras", "Tomar el camino de piedras"),
                "images/start.png", false, false
        ));

        // --- Rama de las Piedras ---
        gameLogic.put("piedras", new GameNode(
                "Las piedras te llevan a un río caudaloso. No hay puente, pero ves un viejo bote de madera atado a la orilla.",
                Map.of("bote", "Intentar cruzar con el bote", "ignorar", "Ignorar el bote y buscar otro camino"),
                "images/river.png", false, false
        ));

        gameLogic.put("ignorar", new GameNode(
                "Te adentras más en el bosque oscuro y te pierdes. El bosque reclama a otro viajero. Fin del juego.",
                Map.of(),
                "images/forestLostDefeat.png", true, false
        ));

        gameLogic.put("bote", new GameNode(
                "El bote tiene una fuga, pero logras llegar al otro lado. Encuentras un cofre oxidado cerrado con un enigma.",
                Map.of("enigma", "Intentar resolver el enigma", "forzar", "Forzar la cerradura"),
                "images/chest.png", false, false
        ));

        gameLogic.put("forzar", new GameNode(
                "Al forzar la cerradura, activas una trampa de agujas venenosas. Fin del juego.",
                Map.of(),
                "images/defeatByChest.png", true, false
        ));
        gameLogic.put("enigma", new GameNode(
                "El enigma era: \"Hablo sin boca y oigo sin oídos. No tengo cuerpo, pero cobro vida con el viento. " +
                        "¿Qué soy?\" " +
                        "-Respondes \"un eco\". El cofre se abre revelando un mapa. ¡Has encontrado el camino a casa! ¡Victoria!",
                Map.of(),
                "images/victoryByChest.png", true, true
        ));

        // --- Rama de las Setas ---
        gameLogic.put("setas", new GameNode(
                "Las setas te guían a una cueva silenciosa. Dentro, un Golem de piedra duerme. " +
                        "Puedes intentar pasar en sigilo o agarrar el cristal brillante de su pecho.",
                Map.of("sigilo", "Pasar en sigilo", "cristal", "Agarrar el cristal"),
                "images/cave.png", false, false
        ));
        gameLogic.put("cristal", new GameNode(
                "El Golem despierta y te aplasta. Tu avaricia ha sido tu perdición. Fin del juego.",
                Map.of(),
                "images/golemWrathDefeat.png", true, false
        ));
        gameLogic.put("sigilo", new GameNode(
                "Al otro lado de la cueva, encuentras una fuente mística. Un cartel dice: 'Bebe para saber' y 'Toca para sanar'.",
                Map.of("beber", "Beber de la fuente", "tocar", "Tocar el agua"),
                "images/fountain.png", false, false
        ));
        gameLogic.put("tocar", new GameNode(
                "Tus heridas se curan, pero la puerta de salida se sella. Estás atrapado para siempre. Fin del juego.",
                Map.of(),
                "images/sealedFateDefeat.png", true, false
        ));
        gameLogic.put("beber", new GameNode(
                "Bebes el agua y obtienes una visión clara del laberinto del bosque. Sales sin problemas. ¡Victoria!",
                Map.of(),
                "images/forestExitVictory.png", true, true
        ));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);

        // --- Lógica para cambiar nombre ---
        String action = req.getParameter("action");
        if ("changeName".equals(action)) {
            session.invalidate(); // limpia completamente la sesión
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }

        // --- Lógica de inicio de juego (reiniciar) ---
        if (req.getParameter("restart") != null) {

            // Inicializa contadores si no existen (solo se hace la primera vez)
            initializeStats(session);

            // Inicia temporizador y nodo
            session.setAttribute("startTime", System.currentTimeMillis());
            session.setAttribute("currentNode", "start");

            // Incrementar partidas jugadas
            Integer gamesPlayed = (Integer) session.getAttribute("gamesPlayed");
            session.setAttribute("gamesPlayed", gamesPlayed + 1);
        }

        // Si no hay nodo actual (y no se está reiniciando), enviar al inicio
        if (session.getAttribute("currentNode") == null) {
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }

        String currentNodeKey = (String) session.getAttribute("currentNode");
        GameNode node = gameLogic.get(currentNodeKey);

        if (node.isEndNode()) {
            // --- Lógica de estadísticas de fin de partida ---

            // Calcular duración
            long startTime = (Long) session.getAttribute("startTime");
            long durationInSeconds = (System.currentTimeMillis() - startTime) / 1000;
            req.setAttribute("gameDuration", durationInSeconds);

            // Incrementar victoria o derrota
            if (node.isVictory()) {
                Integer wins = (Integer) session.getAttribute("gamesWon");
                session.setAttribute("gamesWon", wins + 1);
            } else {
                Integer losses = (Integer) session.getAttribute("gamesLost");
                session.setAttribute("gamesLost", losses + 1);
            }

            // Limpiar el nodo actual para forzar reinicio
            session.removeAttribute("currentNode");

            req.setAttribute("message", node.getText());
            req.setAttribute("imageUrl", node.getImageUrl());
            req.getRequestDispatcher("/jsp/result.jsp").forward(req, resp);
        } else {
            req.setAttribute("node", node);
            req.getRequestDispatcher("/jsp/game.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        // Manejar el envío del nombre desde index.jsp ---
        String playerName = req.getParameter("playerName");
        if (playerName != null && !playerName.isEmpty()) {
            // Es un jugador nuevo registrando su nombre
            session.setAttribute("playerName", playerName);

            // Forzar el inicio del juego
            initializeStats(session);
            session.setAttribute("startTime", System.currentTimeMillis());
            session.setAttribute("currentNode", "start");
            session.setAttribute("gamesPlayed", 1); // Es su primera partida

            resp.sendRedirect(req.getContextPath() + "/game");
            return;
        }

        // --- Lógica existente para las elecciones del juego ---
        String choice = req.getParameter("choice");
        if (choice != null && gameLogic.containsKey(choice)) {
            session.setAttribute("currentNode", choice);
        } else {
            // Opción inválida, volver al inicio
            session.setAttribute("currentNode", "start");
        }
        resp.sendRedirect(req.getContextPath() + "/game");
    }

    // --- Helper para inicializar estadísticas ---
    private void initializeStats(HttpSession session) {
        if (session.getAttribute("gamesPlayed") == null) {
            session.setAttribute("gamesPlayed", 0);
        }
        if (session.getAttribute("gamesWon") == null) {
            session.setAttribute("gamesWon", 0);
        }
        if (session.getAttribute("gamesLost") == null) {
            session.setAttribute("gamesLost", 0);
        }
    }
}
