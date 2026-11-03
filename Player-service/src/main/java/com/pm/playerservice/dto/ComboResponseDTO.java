package com.pm.playerservice.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.pm.playerservice.dto.amp.AmpSpecResponseDTO;
import com.pm.playerservice.dto.dac.DacSpecResponseDTO;
import com.pm.playerservice.model.PlayerType;

@JsonPropertyOrder({
        "id","brand","model","playerType","dacSpec","ampSpec"
})
public class ComboResponseDTO {
    private Long id;
    private String brand;
    private String model;
    private PlayerType playerType;
    private DacSpecResponseDTO dacSpec;
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

    public DacSpecResponseDTO getDacSpec() {
        return dacSpec;
    }

    public void setDacSpec(DacSpecResponseDTO dacSpec) {
        this.dacSpec = dacSpec;
    }

    public AmpSpecResponseDTO getAmpSpec() {
        return ampSpec;
    }

    public void setAmpSpec(AmpSpecResponseDTO ampSpec) {
        this.ampSpec = ampSpec;
    }
}
