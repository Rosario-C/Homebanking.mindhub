package com.mindhub.homebanking.services.implement;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CardServiceImp implements CardService {
    @Autowired
    CardRepository cardRepository;


    @Override
    public Card getCard(Long id) {
        return cardRepository.findById(id).orElse(null);
    }



    @Override
    public void saveCard(Card card) {
        cardRepository.save(card);
    }
}
