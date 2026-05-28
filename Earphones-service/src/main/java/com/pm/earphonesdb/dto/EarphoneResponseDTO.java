package com.pm.earphonesdb.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class EarphoneResponseDTO {
    private long id;
    private String brand;
    private String model;
    private BigDecimal msrp;

    private List<EarphoneDriverResponseDTO> drivers = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public BigDecimal getMsrp() {
        return msrp;
    }

    public void setMsrp(BigDecimal msrp) {
        this.msrp = msrp;
    }

    public List<EarphoneDriverResponseDTO> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<EarphoneDriverResponseDTO> drivers) {
        this.drivers = drivers;
    }
}
