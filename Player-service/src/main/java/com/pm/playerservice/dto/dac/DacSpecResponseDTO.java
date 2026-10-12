package com.pm.playerservice.dto.dac;

import com.pm.playerservice.dto.ChipResponseDTO;

import java.util.List;

public class DacSpecResponseDTO {
        private Integer bitDepth;
        private boolean overSampling;
        private List<ChipResponseDTO> chips;

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

        public List<ChipResponseDTO> getChips() {
            return chips;
        }

        public void setChips(List<ChipResponseDTO> chips) {
            this.chips = chips;
        }
}
