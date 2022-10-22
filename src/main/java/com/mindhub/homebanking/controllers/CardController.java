package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.dto.CardDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;
import static com.mindhub.homebanking.utils.Util.getRandomNumber;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    private CardService cardService;

    @Autowired
    private ClientService clientService;

    @PatchMapping(  "/cards/{id}")
    public ResponseEntity<Object> deleteCard (@PathVariable Long id, Authentication authentication) {
        Client client = clientService.getClientByEmail(authentication.getName());
        Card card = cardService.getCard(id);
        if(card == null){
            return new ResponseEntity<>("The card does not exist", HttpStatus.FORBIDDEN);
        }
        if(!client.getCards().contains(card)){
            return new ResponseEntity<>("This card does not belong to this client", HttpStatus.FORBIDDEN);
        }
        if(!card.isActive()){

            return new ResponseEntity<>("This card does not exist", HttpStatus.FORBIDDEN);
        }
        cardService.saveCard(card);
        return new ResponseEntity<>("Card deleted",HttpStatus.ACCEPTED);
    }

    @PatchMapping("/clients/current/cards")
    public void expired(Authentication authentication){
        Client client = clientService.getClientByEmail(authentication.getName());
        Set<Card> cards = client.getCards();
        cards.stream().forEach(card -> {
            if(LocalDateTime.now().isAfter(card.getThruDate())){
                card.setExpired(true);
            }
            cardService.saveCard(card);
        });
    }

    @PostMapping( "/clients/current/cards")
    public ResponseEntity<Object> createcard(

            @RequestParam CardType type,
            @RequestParam CardColor color,
            Authentication authentication) {

        Client client = clientService.getClientByEmail(authentication.getName());
         Set<Card> cards = client.getCards();

        Set<Card> cardDebit= cards.stream().filter(card ->card.getType() == CardType.DÉBITO).collect(Collectors.toSet());
        Set<Card> cardCredit = cards.stream().filter(card ->card.getType() == CardType.CRÉDITO).collect(Collectors.toSet());


        if(cardDebit.size() >= 3 && type == CardType.DÉBITO){
            return new ResponseEntity<>("You can not have more than 3 cards", HttpStatus.FORBIDDEN);

        }
        if(cardCredit.size() >= 3 && type == CardType.CRÉDITO){
            return new ResponseEntity<>("You can not have more than 3 cards", HttpStatus.FORBIDDEN);

        }



        cardService.saveCard(new Card(client.getFirstName() +" "+ client.getLastName(), type, color, getRandomNumber(1000,9999) +"-"+ getRandomNumber(1000, 9999) +"-"+ getRandomNumber(1000, 9999) +"-"+ getRandomNumber(1000, 9999), getRandomNumber(100, 999), LocalDateTime.now(), LocalDateTime.now().plusYears(5), client, false ));

        return new ResponseEntity<>("Card created",HttpStatus.CREATED);

    }
    @GetMapping( "/clients/current/cards")
    Set<CardDTO> clientCard(Authentication authentication){
        Client client = clientService.getClientByEmail(authentication.getName());
        return client.getCards().stream().map(card -> new CardDTO(card)).collect(Collectors.toSet());
    }


}
