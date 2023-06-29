package com.marriott.webapp.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Entity
@DiscriminatorValue(value = "starwood")
public class StarwoodMember extends User {

    public void setUsername(final String username) {
        this.credentials.setUsername(username);
    }

    public void setPassword(final String password) {
        this.credentials.setPassword(password);
    }
}
