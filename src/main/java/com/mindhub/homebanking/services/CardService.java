package com.mindhub.homebanking.services;
import com.mindhub.homebanking.models.Card;

import java.time.LocalDateTime;

public interface CardService {

    Card getCard(Long id);



    void saveCard(Card card );

}
