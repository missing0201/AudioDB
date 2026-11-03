package com.pm.playerservice.dto.dac;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.pm.playerservice.model.PlayerType;

@JsonPropertyOrder({
        "id","brand","model","playerType","dacSpec"
})
public class DacResponseDTO {
    private Long id;
    private String brand;
    private String model;
    private PlayerType playerType;
    @JsonProperty("dacSpec")
    private DacSpecResponseDTO dacSpec;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public void setPlayerType(PlayerType playerType) {
        this.playerType = playerType;
    }

    public DacSpecResponseDTO getDacSpec() {
        return dacSpec;
    }

    public void setDacSpec(DacSpecResponseDTO dacSpec) {
        this.dacSpec = dacSpec;
    }
}
