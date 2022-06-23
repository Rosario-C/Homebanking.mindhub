package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.dto.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Set;
import static com.mindhub.homebanking.utils.Util.getRandomNumber;
import static java.util.stream.Collectors.toSet;

@RestController
@RequestMapping("/api")

public class AccountController {
    @Autowired
   public AccountService accountService;
    @Autowired
    public ClientService clientService;
    @Autowired
    public AccountRepository accountRepository;
    @RequestMapping("/accounts")
    public Set<AccountDTO> getAll() {

        return accountService.getAccountsDTO();
    }

    @RequestMapping("/accounts/{id}")
    public AccountDTO getAccount (@PathVariable Long id) {
        return accountService.getAccountDTO(id);
    }


    @PatchMapping( "/accounts/{id}")
    public ResponseEntity<Object> deleteAccount(Authentication authentication, @PathVariable Long id){
       Client client= clientService.getClientByEmail(authentication.getName());
       Account account = accountService.getAccount(id);
       if(account == null){
           return new ResponseEntity<>("The account does not exist", HttpStatus.FORBIDDEN);
       }
       if(!client.getAccounts().contains(account)){
           return new ResponseEntity<>("This account does not belong to this user", HttpStatus.FORBIDDEN);
       }
       if(!account.isAccountActive()){
             return new ResponseEntity<>("This account does not exist", HttpStatus.FORBIDDEN);
       }
       if(account.getBalance()>0){
           return new ResponseEntity<>("Please transfer the balance to another account", HttpStatus.FORBIDDEN);
       }
       if(account.isAccountActive()){
           account.setAccountActive(false);
       }
        accountService.saveAccount(account);
        return new ResponseEntity<>("Account deleted",HttpStatus.ACCEPTED);

    }



    @PostMapping("/clients/current/accounts")

    public ResponseEntity<Object> newAccount(Authentication authentication){
            Client client = clientService.getClientCurrent(authentication);

        if (client.getAccounts().size() >= 3) {

            return new ResponseEntity<>("You can't have more then 3 accounts", HttpStatus.FORBIDDEN);
        }
        accountService.saveAccount(new Account(client,"VIN"+ getRandomNumber(100000,999999), LocalDateTime.now(),0,true));

        return new ResponseEntity<>(HttpStatus.CREATED);

    }
    @GetMapping( "/clients/current/accounts")
    Set<AccountDTO> accountSet(Authentication authentication){
        Client client = clientService.getClientByEmail(authentication.getName());
            return client.getAccounts().stream().map(account -> new AccountDTO(account)).collect(toSet());
    }

}
