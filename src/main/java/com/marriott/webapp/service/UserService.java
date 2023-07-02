package com.marriott.webapp.service;

import com.marriott.webapp.model.CreditCard;
import com.marriott.webapp.model.MemberRepository;
import com.marriott.webapp.model.StarwoodMember;
import com.marriott.webapp.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final MemberRepository userRepository;

    @Transactional
    public CreditCard addCreditCard(final long memberId, final CreditCard card) {
        final StarwoodMember member = (StarwoodMember) userRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member id"));

        member
            .getCreditCards()
            .add(card);

        card.setUser(member);

        userRepository.save(member);

        return card;
    }

    public void deleteMember(long memberId) {
        User user = userRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member id"));

        if (!(user instanceof StarwoodMember)) {
            throw new IllegalArgumentException("The user with id " + memberId + " is not a Starwood member");
        }

        userRepository.delete(user);
    }
}