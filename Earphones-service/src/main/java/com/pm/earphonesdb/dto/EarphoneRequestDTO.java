package com.pm.earphonesdb.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class EarphoneRequestDTO {
    @NotBlank
    @Size(max=30,message = "Name cannot exceed 30 characters")
    private String brand;

    @NotBlank
    @Size(max=30,message = "Name cannot exceed 30 characters")
    private String model;

    @PositiveOrZero
    private BigDecimal msrp;

    private List<EarphoneDriverRequestDTO> drivers = new ArrayList<>();

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

    public BigDecimal getMsrp() {
        return msrp;
    }

    public void setMsrp(BigDecimal msrp) {
        this.msrp = msrp;
    }

    public List<EarphoneDriverRequestDTO> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<EarphoneDriverRequestDTO> drivers) {
        this.drivers = drivers;
    }

}
