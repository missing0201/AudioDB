package com.pm.earphonesdb.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class EarphoneRequestDTO {
    @NotNull
    private Long brandId;

    @NotBlank
    @Size(max=30,message = "Name cannot exceed 30 characters")
    private String model;

    @NotNull
    private BigDecimal msrp;

    private List<EarphoneDriverRequestDTO> drivers = new ArrayList<>();

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
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
