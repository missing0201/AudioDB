package com.pm.playerservice.model.amp;

import com.pm.playerservice.model.Player;
import jakarta.persistence.*;

@Entity
public class AmpSpec {

    @Id
    private Long playerId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "player_id")
    private Player player;

    private Double powerOutput;

    private Integer minImpedance;

    private Integer maxImpedance;

    @Enumerated(EnumType.STRING)
    private AmpClass ampClass;

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Double getPowerOutput() {
        return powerOutput;
    }

    public void setPowerOutput(Double powerOutput) {
        this.powerOutput = powerOutput;
    }

    public Integer getMinImpedance() {
        return minImpedance;
    }

    public void setMinImpedance(Integer minImpedance) {
        this.minImpedance = minImpedance;
    }

    public Integer getMaxImpedance() {
        return maxImpedance;
    }

    public void setMaxImpedance(Integer maxImpedance) {
        this.maxImpedance = maxImpedance;
    }

    public AmpClass getAmpClass() {
        return ampClass;
    }

    public void setAmpClass(AmpClass ampClass) {
        this.ampClass = ampClass;
    }
}
