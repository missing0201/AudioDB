package com.pm.soundsignatureservice.model;

import jakarta.persistence.*;

@Entity
@Table(name = "signature")
public class SignatureEntity {

    @Id
    @Column(name = "earphone_id")
    private Long earphoneId;

    @Column(name = "primary_signature")
    private String primarySignature;

    @Column(name = "bass_score")
    private float bassScore;

    @Column(name = "mids_score")
    private float midsScore;

    @Column(name = "treble_score")
    private float trebleScore;

    private String description;

    public SignatureEntity() {}

    public Long getEarphoneId() { return earphoneId; }
    public void setEarphoneId(Long earphoneId) { this.earphoneId = earphoneId; }

    public String getPrimarySignature() { return primarySignature; }
    public void setPrimarySignature(String primarySignature) { this.primarySignature = primarySignature; }

    public float getBassScore() { return bassScore; }
    public void setBassScore(float bassScore) { this.bassScore = bassScore; }

    public float getMidsScore() { return midsScore; }
    public void setMidsScore(float midsScore) { this.midsScore = midsScore; }

    public float getTrebleScore() { return trebleScore; }
    public void setTrebleScore(float trebleScore) { this.trebleScore = trebleScore; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}