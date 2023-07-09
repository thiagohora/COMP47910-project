package com.marriott.webapp.api;

import com.marriott.webapp.model.CreditCard;
import com.marriott.webapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members/{memberId}/credit-cards")
public class CreditCardController {

    private final UserService userService;

    @PostMapping
    public CreditCard addCreditCard(@PathVariable final long memberId, @RequestBody final CreditCard card) {
        return userService.addCreditCard(memberId, card);
    }
}