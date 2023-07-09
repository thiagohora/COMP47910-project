package com.marriott.webapp.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PROTECTED, onConstructor = @__(@JsonCreator))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
public abstract class User {

    @Embeddable
    @Data
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PROTECTED, onConstructor = @__(@JsonCreator))
    public static class Credentials {
        @Column(unique = true)
        private String username;
        private String password;
        // getters and setters
    }

    @Embeddable
    @Data
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PROTECTED, onConstructor = @__(@JsonCreator))
    public static class Address {
        private String street;
        private String city;
        private String state;
        private String zipcode;
        private String country;
    }

    @Embeddable
    @Data
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PROTECTED, onConstructor = @__(@JsonCreator))
    public static class Contact {
        private String phone;
        @Column(unique = true)
        private String email;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected String name;
    protected String surname;

    @Embedded
    protected Contact contact;

    @Embedded
    protected Address address;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    protected List<CreditCard> creditCards;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    protected List<Reservation> reservations;

    protected Credentials credentials;

    // getters and setters
}