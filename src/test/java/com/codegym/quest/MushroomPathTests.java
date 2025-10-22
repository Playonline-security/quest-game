package com.codegym.quest;

import com.codegym.quest.model.GameNode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas para la ruta de las setas")
class MushroomPathTests extends BaseGameTest {

    @Test
    @DisplayName("Elección 'setas' en el inicio debe llevar a la cueva")
    void testStartNode_whenChoosingMushrooms_leadsToCave() {
        GameNode nextNode = gameLogic.get("setas");
        assertNotNull(nextNode);
        assertTrue(nextNode.getText().contains("cueva silenciosa"));
        assertFalse(nextNode.isEndNode());
    }

    @Test
    @DisplayName("En la cueva, agarrar el cristal debe ser una derrota")
    void testCaveNode_whenGrabbingCrystal_isDefeat() {
        GameNode defeatNode = gameLogic.get("cristal");
        assertNotNull(defeatNode);
        assertTrue(defeatNode.isEndNode());
        assertFalse(defeatNode.isVictory());
    }

    @Test
    @DisplayName("En la fuente, tocar el agua debe ser una derrota")
    void testFountainNode_whenTouchingWater_isDefeat() {
        GameNode defeatNode = gameLogic.get("tocar");
        assertNotNull(defeatNode);
        assertTrue(defeatNode.isEndNode());
        assertFalse(defeatNode.isVictory());
    }

    @Test
    @DisplayName("En la fuente, beber el agua debe ser una victoria")
    void testFountainNode_whenDrinkingFromFountain_isVictory() {
        GameNode victoryNode = gameLogic.get("beber");
        assertNotNull(victoryNode);
        assertTrue(victoryNode.isEndNode());
        assertTrue(victoryNode.isVictory());
    }
}
