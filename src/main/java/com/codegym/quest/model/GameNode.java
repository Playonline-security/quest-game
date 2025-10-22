package com.codegym.quest.model;

import java.util.Map;

public class GameNode {
    private String text;
    private Map<String, String> choices; // Clave: ID de la respuesta, Valor: Texto de la respuesta
    private String imageUrl;
    private boolean isEndNode;
    private boolean isVictory;

    public GameNode(String text, Map<String, String> choices, String imageUrl, boolean isEndNode, boolean isVictory) {
        this.text = text;
        this.choices = choices;
        this.imageUrl = imageUrl;
        this.isEndNode = isEndNode;
        this.isVictory = isVictory;
    }

    public String getText() {
        return text;
    }

    public Map<String, String> getChoices() {
        return choices;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public boolean isEndNode() {
        return isEndNode;
    }

    public boolean isVictory() {
        return isVictory;
    }
}
