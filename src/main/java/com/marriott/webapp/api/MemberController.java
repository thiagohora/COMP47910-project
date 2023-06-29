package com.marriott.webapp.api;

import com.marriott.webapp.model.StarwoodMember;
import com.marriott.webapp.model.User;
import com.marriott.webapp.service.MemberService;
import com.marriott.webapp.service.RegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<StarwoodMember> register(@RequestBody final RegistrationRequest registrationRequest) {
        final var registeredMember = memberService.register(
                StarwoodMember.builder()
                        .name(registrationRequest.getName())
                        .surname(registrationRequest.getSurname())
                        .contact(User.Contact.builder()
                                .emailAddress(registrationRequest.getContact().getEmailAddress())
                                .phoneNumber(registrationRequest.getContact().getPhoneNumber())
                                .build())
                        .address(User.Address.builder()
                                .street(registrationRequest.getAddress().getStreet())
                                .city(registrationRequest.getAddress().getCity())
                                .state(registrationRequest.getAddress().getState())
                                .zip(registrationRequest.getAddress().getZip())
                                .country(registrationRequest.getAddress().getCountry())
                                .build())
                        .build(),
                registrationRequest.getUsername(),
                registrationRequest.getPassword());

        return ResponseEntity.ok(registeredMember);
    }
}