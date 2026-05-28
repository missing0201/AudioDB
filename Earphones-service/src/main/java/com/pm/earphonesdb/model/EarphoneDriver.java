package com.pm.earphonesdb.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class EarphoneDriver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "earphone_id")
    @JsonBackReference
    private Earphone earphone;

    @ManyToOne
    @JoinColumn(name = "driver_type_id")
    private DriverType driverType;

    private int quantity;

    public long getId() {
        return id;
    }

    public Earphone getEarphone() {
        return earphone;
    }

    public void setEarphone(Earphone earphone) {
        this.earphone = earphone;
    }

    public DriverType getDriverType() {
        return driverType;
    }

    public void setDriverType(DriverType driverType) {
        this.driverType = driverType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
