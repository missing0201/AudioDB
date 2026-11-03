package com.pm.playerservice.dto.amp;

import com.pm.playerservice.model.amp.AmpClass;

public class AmpSpecResponseDTO {
    private double powerOutput;
    private int minImpedance;
    private int maxImpedance;
    private AmpClass ampClass;

    public double getPowerOutput() {
        return powerOutput;
    }

    public void setPowerOutput(double powerOutput) {
        this.powerOutput = powerOutput;
    }

    public int getMinImpedance() {
        return minImpedance;
    }

    public void setMinImpedance(int minImpedance) {
        this.minImpedance = minImpedance;
    }

    public int getMaxImpedance() {
        return maxImpedance;
    }

    public void setMaxImpedance(int maxImpedance) {
        this.maxImpedance = maxImpedance;
    }

    public AmpClass getAmpClass() {
        return ampClass;
    }

    public void setAmpClass(AmpClass ampClass) {
        this.ampClass = ampClass;
    }
}
