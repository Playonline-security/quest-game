package com.codegym.quest;

import com.codegym.quest.model.GameNode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas para la ruta de las piedras")
class StonePathTests extends BaseGameTest {

    @Test
    @DisplayName("Elección 'Piedras' en el inicio debe llevar al río")
    void testStartNode_whenChoosingStones_leadsToRiver() {
        GameNode nextNode = gameLogic.get("piedras");
        assertNotNull(nextNode);
        assertTrue(nextNode.getText().contains("río caudaloso"));
        assertFalse(nextNode.isEndNode());
    }

    @Test
    @DisplayName("En el río, ignorar el bote debe ser una derrota")
    void testRiverNode_whenIgnoringBoat_isDefeat() {
        GameNode defeatNode = gameLogic.get("ignorar");
        assertNotNull(defeatNode);
        assertTrue(defeatNode.isEndNode());
        assertFalse(defeatNode.isVictory());
    }

    @Test
    @DisplayName("En el Cofre, forzar la cerradura debe ser una derrota")
    void testChestNode_whenForcingLock_isDefeat() {
        GameNode defeatNode = gameLogic.get("forzar");
        assertNotNull(defeatNode);
        assertTrue(defeatNode.isEndNode());
        assertFalse(defeatNode.isVictory());
    }

    @Test
    @DisplayName("En el Cofre, resolver el enigma debe ser una victoria")
    void testChestNode_whenSolvingEnigma_isVictory() {
        GameNode victoryNode = gameLogic.get("enigma");
        assertNotNull(victoryNode);
        assertTrue(victoryNode.isEndNode());
        assertTrue(victoryNode.isVictory());
    }
}
