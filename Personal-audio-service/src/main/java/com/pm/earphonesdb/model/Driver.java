package com.pm.earphonesdb.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_earphone_driver_type",
                        columnNames = {"earphone_id", "driver_type_id"}
                ),
                @UniqueConstraint(
                        name = "uq_headphone_driver_type",
                        columnNames = {"headphone_id", "driver_type_id"}
                )
        }
)
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "earphone_id")
    @JsonBackReference
    private Earphone earphone;

    @ManyToOne
    @JoinColumn(name = "headphone_id")
    @JsonBackReference
    private Headphone headphone;

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
