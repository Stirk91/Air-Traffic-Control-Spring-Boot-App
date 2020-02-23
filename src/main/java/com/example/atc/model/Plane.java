package com.example.atc.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

public class Plane {

    private final UUID id;

    @NotBlank
    private final String registration;

    public Plane(@JsonProperty("id") UUID id,
                 @JsonProperty("registration") String registration) {
        this.id = id;
        this.registration = registration;
    }

    public UUID getId() {
        return id;
    }

    public String getRegistration() {
        return registration;
    }

}
