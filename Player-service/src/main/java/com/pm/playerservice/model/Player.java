package com.pm.playerservice.model;

import com.pm.playerservice.model.amp.AmpSpec;
import com.pm.playerservice.model.dac.DacSpec;
import jakarta.persistence.*;
import jakarta.validation.Constraint;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(uniqueConstraints ={
        @UniqueConstraint(columnNames= {"brand_id","model"})
        })
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @Column(nullable = false)
    private String model;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PlayerType playerType;

    @OneToOne(
            mappedBy = "player",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private DacSpec dacSpec;

    @OneToOne(
            mappedBy = "player",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )

    private AmpSpec ampSpec;

    @PrePersist
    @PreUpdate
    public void syncAndValidateType() {
        boolean dac = dacSpec != null;
        boolean amp = ampSpec != null;
        if (!dac && !amp) throw new IllegalStateException("Player needs at least one spec");
        this.playerType = dac && amp ? PlayerType.DAC_AMP
                : dac ? PlayerType.DAC
                  : PlayerType.AMP;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
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

    public DacSpec getDacSpec() {
        return dacSpec;
    }

    public void setDacSpec(DacSpec dacSpec) {
        this.dacSpec = dacSpec;
        if (dacSpec != null) dacSpec.setPlayer(this);
    }

    public AmpSpec getAmpSpec() {
        return ampSpec;
    }

    public void setAmpSpec(AmpSpec ampSpec) {
        this.ampSpec = ampSpec;
        if (ampSpec != null) ampSpec.setPlayer(this);
    }
}
