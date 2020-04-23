package com.example.atc.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotBlank;
import java.util.UUID;
import java.util.Set;

import com.example.atc.model.Taxiway;


public class Gate {
    protected int gate_id;
    @NotBlank
    protected String gate_name;
    protected UUID plane_id;
    Set<Taxiway> taxiways;


    public Gate() {}

    public Gate(@JsonProperty("gate_id") int gate_id,
                @JsonProperty("gate_name") String gate_name,
                @JsonProperty("taxiways") Set<Taxiway> taxiways) {
        this.gate_id = gate_id;
        this.gate_name = gate_name;
        this.taxiways = taxiways;
    }

    public int getGate_id() {
        return gate_id;
    }

    public String getGate_name() {
        return gate_name;
    }

    public UUID getPlane_id() {
        return plane_id;
    }

    public Set<Taxiway> getTaxiways() {
        return taxiways;
    }

    public void setTaxiways(Set<Taxiway> taxiways) {
        this.taxiways = taxiways;
    }
}
