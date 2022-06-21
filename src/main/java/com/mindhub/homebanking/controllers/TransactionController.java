package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import static com.mindhub.homebanking.models.TransactionType.CRÉDITO;
import static com.mindhub.homebanking.models.TransactionType.DÉBITO;


@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionService transactionService;

    @Transactional
    @PostMapping( "/transactions")

    public ResponseEntity<Object> transfer(

            @RequestParam Double amount, @RequestParam String description,   Authentication authentication,

            @RequestParam String originAccountNumber, @RequestParam String destinationAccountNumber) {

            Client client = clientService.getClientCurrent(authentication);

            Account originAccount = accountService.findByNumber(originAccountNumber);
            Account targetAccount = accountService.findByNumber(destinationAccountNumber);


        if (description.isEmpty()) {

            return new ResponseEntity<>("Missing description", HttpStatus.FORBIDDEN);

        }
        if (amount.isNaN() || amount.isInfinite() || amount == 0) {

            return new ResponseEntity<>("no se puede", HttpStatus.FORBIDDEN);

        }
        if (originAccount.getBalance() < amount ) {

            return new ResponseEntity<>("no se pudió, voce no tiene platita mano", HttpStatus.FORBIDDEN);

        }
        if (originAccountNumber.isEmpty()) {

            return new ResponseEntity<>("Missing Origin Account", HttpStatus.FORBIDDEN);

        }
        if (destinationAccountNumber.isEmpty()) {

            return new ResponseEntity<>("Missing target Account", HttpStatus.FORBIDDEN);

        }
        if (accountService.findByNumber(originAccountNumber) == null) {

            return new ResponseEntity<>("The account you are trying to use does not exist", HttpStatus.FORBIDDEN);

        }
        if (accountService.findByNumber(destinationAccountNumber) == null) {

            return new ResponseEntity<>("The account does not exist", HttpStatus.FORBIDDEN);

        }
        if (!client.getAccounts().contains(originAccount)) {

            return new ResponseEntity<>("The owner of the account is not authenticated", HttpStatus.FORBIDDEN);

        }
        if (originAccount == targetAccount) {

            return new ResponseEntity<>("You cannot operate with this account", HttpStatus.FORBIDDEN);

        }
       Transaction transactionC = new Transaction(CRÉDITO, amount, description +" "+ "tranfer received from" + originAccount, LocalDateTime.now(),  targetAccount, originAccount.getBalance()+amount);
        transactionService.saveTransactions(transactionC);

       Transaction transactionD = new Transaction(DÉBITO, -amount,description +" "+ "tranfer sent to" + targetAccount, LocalDateTime.now(), originAccount, originAccount.getBalance()-amount);
        transactionService.saveTransactions(transactionD);

        originAccount.setBalance(originAccount.getBalance() - amount);
        accountService.saveAccount(originAccount);

        targetAccount.setBalance(targetAccount.getBalance() + amount);
        accountService.saveAccount(targetAccount);

        return new ResponseEntity<>("Transfer sent",HttpStatus.CREATED);



    }


}
