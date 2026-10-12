package com.pm.playerservice.model.dac;

import jakarta.persistence.*;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"dac_spec_id","model_id"})})
public class DacChip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dac_spec_id",nullable = false)
    private DacSpec dacSpec;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id",nullable = false)
    private DacChipModel model;

    private int quantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DacSpec getDacSpec() {
        return dacSpec;
    }

    public void setDacSpec(DacSpec dacSpec) {
        this.dacSpec = dacSpec;
    }

    public DacChipModel getModel() {
        return model;
    }

    public void setModel(DacChipModel model) {
        this.model = model;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
