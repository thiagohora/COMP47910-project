package com.marriott.webapp.service;

import com.marriott.webapp.model.User;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Data
public class RegistrationRequest {

    @Data
    public class Address {
        private String street;
        private String city;
        private String state;
        private String zip;
        private String country;
    }

    @Data
    public class Contact {
        private String phoneNumber;
        private String emailAddress;
    }

    private String name;
    private String surname;
    private Contact contact;
    private Address address;

    private String username;
    private String password;

    // getters and setters
}