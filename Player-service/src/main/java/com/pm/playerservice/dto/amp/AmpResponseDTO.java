package com.pm.playerservice.dto.amp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.pm.playerservice.model.PlayerType;

@JsonPropertyOrder({
        "id","brand","model","playerType","ampSpec"
})
public class AmpResponseDTO {
    private Long id;
    private String brand;
    private String model;
    private PlayerType playerType;
    @JsonProperty("ampSpec")
    private AmpSpecResponseDTO ampSpec;

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

    public AmpSpecResponseDTO getAmpSpec() {
        return ampSpec;
    }

    public void setAmpSpec(AmpSpecResponseDTO ampSpec) {
        this.ampSpec = ampSpec;
    }
}
