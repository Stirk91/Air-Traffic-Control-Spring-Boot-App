package com.example.atc.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class PlaneExtended extends Plane {

    String runway_name;
    String taxiway_name;
    String gate_name;

    public PlaneExtended() {
        super();
    }

    public PlaneExtended(@JsonProperty("plane_id") UUID id,
                         @JsonProperty("tail_number") String tail_number,
                         @JsonProperty("state") String state,
                         @JsonProperty("last_action") long last_action,
                         @JsonProperty("distance") int distance,
                         @JsonProperty("altitude") int altitude,
                         @JsonProperty("speed") int speed,
                         @JsonProperty("heading") int heading,
                         @JsonProperty("runway_name") String runway_name,
                         @JsonProperty("taxiway_name") String taxiway_name,
                         @JsonProperty("gate_name") String gate_name) {

        this.id = id;
        this.tail_number = tail_number;
        this.state = state;
        this.last_action = last_action;
        this.distance = distance;
        this.altitude = altitude;
        this.speed = speed;
        this.heading = heading;
        this.runway_name = runway_name;
        this.taxiway_name = taxiway_name;
        this.gate_name = gate_name;
    }




    public UUID getId() {
        return id;
    }
    public String getTail_number() {
        return tail_number;
    }
    public String getState() {
        return state;
    }
    public long getLast_action() {return last_action; }
    //public String getFlightNumber() { return flightNumber; }
    //public String getPlaneClass() {return planeClass; }
    public int getAltitude() {
        return altitude;
    }
    public int getDistance() {
        return distance;
    }
    public int getSpeed() {
        return speed;
    }
    public int getHeading() {
        return heading;
    }
    public String getRunway_name() {return runway_name; }
    public String getTaxiway_name() {return taxiway_name; }
    public String getGate_name() {return gate_name; }

    public void setId(UUID id) { this.id = id; }
    public void setTail_number(String tail_number) { this.tail_number = tail_number; }
    public void setRunway_name(String runway_name) { this.runway_name = runway_name; }
    public void setTaxiway_name(String taxiway_name) { this.taxiway_name = taxiway_name; }
    public void setGate_name(String gate_name) { this.gate_name = gate_name; }

}
