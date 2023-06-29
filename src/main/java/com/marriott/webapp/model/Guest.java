package com.marriott.webapp.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Entity
@DiscriminatorValue(value = "guest")
public class Guest extends User {

    // getters and setters
}
