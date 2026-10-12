package com.pm.playerservice.model.dac;

import com.pm.playerservice.model.Player;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "dac_spec")
public class DacSpec {

    @Id
    private Long playerId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "player_id")
    private Player player;

    @OneToMany(mappedBy = "dacSpec", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<DacChip> dacChips = new ArrayList<>();


    private Integer bitDepth;

    private boolean overSampling;

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

    public List<DacChip> getDacChips() {
        return dacChips;
    }

    public void setDacChips(List<DacChip> dacChips) {
        this.dacChips = dacChips;
    }

    public Integer getBitDepth() {
        return bitDepth;
    }

    public void setBitDepth(Integer bitDepth) {
        this.bitDepth = bitDepth;
    }

    public boolean isOverSampling() {
        return overSampling;
    }

    public void setOverSampling(boolean overSampling) {
        this.overSampling = overSampling;
    }
}
