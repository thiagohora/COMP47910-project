package com.marriott.webapp.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue(value = "starwood")
public class StarwoodMember extends User {

    public void setUsername(final String username) {
        if (this.credentials == null) {
            this.credentials = new Credentials();
        }

        this.credentials.setUsername(username);
    }

    public void setPassword(final String password) {
        if (this.credentials == null) {
            this.credentials = new Credentials();
        }

        this.credentials.setPassword(password);
    }
}
