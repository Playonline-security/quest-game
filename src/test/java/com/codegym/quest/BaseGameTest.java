package com.codegym.quest;

import com.codegym.quest.model.GameNode;
import org.junit.jupiter.api.BeforeAll;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class BaseGameTest {

    protected static final Map<String, GameNode> gameLogic = new LinkedHashMap<>();

    @BeforeAll
    static void setUpGameLogic() {
        if (!gameLogic.isEmpty()) {
            return;
        }

        // --- Lógica del juego ---
        gameLogic.put("start", new GameNode("Estás frente al Bosque del Eco...", Map.of("setas",
                "Seguir las setas brillantes", "piedras", "Tomar el camino de piedras"),
                "images/start.jpg", false, false));

        // --- Rama de las piedras ---
        gameLogic.put("piedras", new GameNode("Las piedras te llevan a un río caudaloso...",
                Map.of("bote", "Intentar cruzar con el bote", "ignorar", "Ignorar el bote..."),
                "images/river.jpg", false, false));
        gameLogic.put("ignorar", new GameNode("Derrota.",
                Map.of(), "images/defeat.jpg",
                true, false));
        gameLogic.put("bote", new GameNode("El bote tiene una fuga...",
                Map.of("enigma", "Intentar resolver el enigma", "forzar", "Forzar la cerradura"),
                "images/chest.jpg", false, false));
        gameLogic.put("forzar", new GameNode("Derrota.",
                Map.of(), "images/defeat.jpg",
                true, false));
        gameLogic.put("enigma", new GameNode("Victoria.",
                Map.of(), "images/victory.jpg",
                true, true));

        // --- Rama de las setas ---
        gameLogic.put("setas", new GameNode("Las setas te guían a una cueva silenciosa...",
                Map.of("sigilo", "Pasar en sigilo", "cristal", "Agarrar el cristal"),
                "images/cave.jpg", false, false));
        gameLogic.put("cristal", new GameNode("Derrota.",
                Map.of(), "images/defeat.jpg",
                true, false));
        gameLogic.put("sigilo", new GameNode("Al otro lado de la cueva, encuentras una fuente...",
                Map.of("beber", "Beber de la fuente", "tocar", "Tocar el agua"),
                "images/fountain.jpg", false, false));
        gameLogic.put("tocar", new GameNode("Derrota.",
                Map.of(), "images/defeat.jpg",
                true, false));
        gameLogic.put("beber", new GameNode("Victoria.",
                Map.of(), "images/victory.jpg",
                true, true));
    }
}
