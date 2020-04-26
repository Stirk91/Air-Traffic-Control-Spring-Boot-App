package com.example.atc.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotBlank;
import java.util.UUID;
import java.util.Set;

import com.example.atc.model.Gate;


public class Taxiway {
    protected int taxiway_id;
    @NotBlank
    protected String taxiway_name;
    protected UUID plane_id;
    //protected Set<Gate> gates;

    
    public Taxiway() {}

    public Taxiway(@JsonProperty("taxiway_id") int taxiway_id,
                   @JsonProperty("taxiway_name") String taxiway_name,
                   //@JsonProperty("gates") Set<Gate> gates,
                   @JsonProperty("plane_id") UUID plane_id) {

        this.taxiway_id = taxiway_id;
        this.taxiway_name = taxiway_name;
        this.plane_id = plane_id;
        //this.gates = gates;
    }

    public int getTaxiway_id() {
        return taxiway_id;
    }
    public String getTaxiway_name() {
        return taxiway_name;
    }
    public UUID getPlane_id() {
        return plane_id;
    }

    public void setPlane_id(UUID plane_id) { this.plane_id = plane_id; }

    //public Set<Gate> getGates() { return gates; }
    // public void setGates(Set<Gate> gates) { this.gates = gates;  }

}
