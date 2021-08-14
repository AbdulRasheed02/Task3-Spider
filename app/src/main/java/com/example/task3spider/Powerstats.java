package com.example.task3spider;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Powerstats {
    @SerializedName("intelligence")
    @Expose
    private Integer intelligence;
    @SerializedName("strength")
    @Expose
    private Integer strength;
    @SerializedName("speed")
    @Expose
    private Integer speed;
    @SerializedName("durability")
    @Expose
    private Integer durability;
    @SerializedName("power")
    @Expose
    private Integer power;
    @SerializedName("combat")
    @Expose
    private Integer combat;

    public Integer getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(Integer intelligence) {
        this.intelligence = intelligence;
    }

    public Integer getStrength() {
        return strength;
    }

    public void setStrength(Integer strength) {
        this.strength = strength;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Integer getDurability() {
        return durability;
    }

    public void setDurability(Integer durability) {
        this.durability = durability;
    }

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

    public Integer getCombat() {
        return combat;
    }

    public void setCombat(Integer combat) {
        this.combat = combat;
    }
}
