package com.marriott.webapp.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue(value = StarwoodMember.STARWOOD)
public class StarwoodMember extends User {

    public static final String STARWOOD = "starwood";

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

    @Override
    public String getType() {
        return STARWOOD;
    }
}
