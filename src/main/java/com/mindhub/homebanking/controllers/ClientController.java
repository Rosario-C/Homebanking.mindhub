package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.dto.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
   public class ClientController {


    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;

    @RequestMapping("/clients")
    public List<ClientDTO> getAll() {
        return clientService.getClientsDTO();
    }

    @RequestMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id) {
        return clientService.getClientDTO(id);
    }

        @Autowired
        private PasswordEncoder passwordEncoder;


        @PostMapping("/clients")

        public ResponseEntity<Object> register(

                @RequestParam String firstName, @RequestParam String lastName,

                @RequestParam String email, @RequestParam String password) {


            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {

                return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);

            }

            if (clientService.getClientByEmail(email) != null) {

                return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);

            }


            Client newClient = new Client(firstName, lastName, email, passwordEncoder.encode(password));
            clientService.saveClient(newClient);

            Account newAccount = new Account(newClient,"VIN"+ Util.getRandomNumber(10000000, 99999999), LocalDateTime.now(), 0d,true);
            accountService.saveAccount(newAccount);
           // newClient.addAccounts(newAccount);
            return new ResponseEntity<>(HttpStatus.CREATED);

        }

    @RequestMapping("/clients/current")
    public ClientDTO getClient(Authentication authentication) {
        Client autentico = clientService.getClientCurrent(authentication);
        return new ClientDTO(autentico);
    }
    }

