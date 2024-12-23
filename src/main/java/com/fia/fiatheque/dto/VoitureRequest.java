package com.fia.fiatheque.dto;

public class VoitureRequest {

    private String name;
    private int speedLimit;
    private Long piloteId;

    // Getters et Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSpeedLimit() {
        return speedLimit;
    }

    public void setSpeedLimit(int speedLimit) {
        this.speedLimit = speedLimit;
    }

    public Long getPiloteId() {
        return piloteId;
    }

    public void setPiloteId(Long piloteId) {
        this.piloteId = piloteId;
    }
}
