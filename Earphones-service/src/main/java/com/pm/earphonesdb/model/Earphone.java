package com.pm.earphonesdb.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "earphone",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"brand", "model"})
        }
)
public class Earphone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String brand;

    @NotNull
    private String model;

    @NotNull
    @PositiveOrZero
    private BigDecimal msrp;

    @OneToMany(mappedBy = "earphone", cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonManagedReference
    private List<EarphoneDriver> drivers = new ArrayList<>();

    public long getId() {
        return id;
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

    public List<EarphoneDriver> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<EarphoneDriver> drivers) {
        this.drivers = drivers;
    }
}
