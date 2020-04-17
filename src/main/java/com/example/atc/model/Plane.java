package com.example.atc.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

public class Plane {

    private final UUID id;

    @NotBlank
    private final String tail_number;

    private final String state;

    private final String last_action;

    private final int distance;

    private final int altitude;

    private final int speed;

    private final int heading;


    public Plane(@JsonProperty("plane_id") UUID id,
                 @JsonProperty("tail__number") String tail_number,
                 @JsonProperty("state") String state,
                 @JsonProperty("last_action") String last_action,
                 @JsonProperty("distance") int distance,
                 @JsonProperty("altitude") int altitude,
                 @JsonProperty("speed") int speed,
                 @JsonProperty("heading") int heading) {

        this.id = id;
        this.tail_number = tail_number;
        this.state = state;
        this.last_action = last_action;
        this.distance = distance;
        this.altitude = altitude;
        this.speed = speed;
        this.heading = heading;
    }

    public UUID getId() {
        return id;
    }

    public String getTail_number() {
        return tail_number;
    }

    public String getState() {
        return tail_number;
    }

    public String getLast_action() {
        return last_action;
    }

    public int getDistance() {
        return distance;
    }

    public int getAltitude() {
        return altitude;
    }

    public int getSpeed() {
        return speed;
    }

    public int getHeading() {
        return heading;
    }


}
