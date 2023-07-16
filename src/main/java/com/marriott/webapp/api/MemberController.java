package com.marriott.webapp.api;

import com.marriott.webapp.model.StarwoodMember;
import com.marriott.webapp.model.User;
import com.marriott.webapp.service.MemberService;
import com.marriott.webapp.service.contracts.RegistrationRequest;
import com.marriott.webapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<StarwoodMember> register(@RequestBody final RegistrationRequest registrationRequest) {
        final var registeredMember = memberService.register(
                StarwoodMember.builder()
                        .name(registrationRequest.getName())
                        .surname(registrationRequest.getSurname())
                        .contact(User.Contact.builder()
                                .email(registrationRequest.getContact().getEmail())
                                .phone(registrationRequest.getContact().getPhone())
                                .build())
                        .address(User.Address.builder()
                                .street(registrationRequest.getAddress().getStreet())
                                .city(registrationRequest.getAddress().getCity())
                                .state(registrationRequest.getAddress().getState())
                                .zipcode(registrationRequest.getAddress().getZipcode())
                                .country(registrationRequest.getAddress().getCountry())
                                .build())
                        .build(),
                registrationRequest.getUsername(),
                registrationRequest.getPassword());

        return ResponseEntity.ok(registeredMember);
    }

    @DeleteMapping
    @PreAuthorize("isAuthenticated()")
    public void deleteMember() {
        userService.deleteMember();
    }
}