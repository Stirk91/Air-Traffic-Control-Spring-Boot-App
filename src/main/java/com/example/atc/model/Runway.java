package com.example.atc.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotBlank;
import java.util.UUID;


public class Runway {
    protected int runway_id;
    @NotBlank
    protected String runway_name;
    protected UUID plane_id;
    

    public Runway() {}

    public Runway(@JsonProperty("runway_id") int runway_id,
                  @JsonProperty("runway_name") String runway_name,
                  @JsonProperty("plane_id") UUID plane_id) {
        this.runway_id = runway_id;
        this.runway_name = runway_name;
        this.plane_id = plane_id;
    }

    public int getRunway_id() {
        return runway_id;
    }

    public String getRunway_name() {
        return runway_name;
    }

    public UUID getPlane_id() {
        return plane_id;
    }
}
