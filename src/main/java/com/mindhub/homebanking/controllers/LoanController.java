package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dto.ClientDTO;
import com.mindhub.homebanking.dto.LoanApplicationDTO;
import com.mindhub.homebanking.dto.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import com.mindhub.homebanking.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    public LoanService loanService;

    @Autowired
    private ClientLoanService clientLoanService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @RequestMapping("/loans")
    public Set<LoanDTO> getAll() {
        return loanService.getLoansDTO();
    }

   @Transactional
    @PostMapping( "/loans")
    public ResponseEntity<Object> requestLoan(

            Authentication authentication,
            @RequestBody LoanApplicationDTO loanApplicationDTO
            )
    {
        Client client = clientService.getClientCurrent(authentication);
        Account targetAccount = accountService.findByNumber(loanApplicationDTO.getBorrowerAccount());
        Loan loanTypes = loanService.getLoan(loanApplicationDTO.getLoanId());

        if (targetAccount == null) {

            return new ResponseEntity<>("The account does not exist", HttpStatus.FORBIDDEN);
        }
        if (!client.getAccounts().contains(targetAccount)) {

            return new ResponseEntity<>("The owner of the account is not authenticated", HttpStatus.FORBIDDEN);

        }
        if(loanApplicationDTO.getAmount() <= 0){
            return new ResponseEntity<>("Enter an Amount", HttpStatus.FORBIDDEN);
        }
        if(loanTypes.getMaxAmount() < loanApplicationDTO.getAmount()){
            return new ResponseEntity<>("The amount es mayor a lo que te podemos prestar ", HttpStatus.FORBIDDEN);
        }
        if(loanApplicationDTO.getPayments() <= 0){
            return new ResponseEntity<>("Enter a payments", HttpStatus.FORBIDDEN);
        }
        if(!loanService.getLoans().contains(loanTypes)){
            return new ResponseEntity<>("The loan does not exist", HttpStatus.FORBIDDEN);
        }
        if(!loanTypes.getPayments().contains(loanApplicationDTO.getPayments())){
            return new ResponseEntity<>("The payments are not available", HttpStatus.FORBIDDEN);
        }
       ClientLoan clientLoanNew = new ClientLoan(loanApplicationDTO.getAmount()*1.2, loanApplicationDTO.getPayments(),client,loanTypes );
        clientLoanService.saveClientLoan(clientLoanNew);
        Transaction clientLoan = new Transaction(TransactionType.CRÉDITO, loanApplicationDTO.getAmount(),loanApplicationDTO.getLoanId() +" " + "Loan approved", LocalDateTime.now(),targetAccount, targetAccount.getBalance()+loanApplicationDTO.getAmount());
        transactionService.saveTransactions(clientLoan);
        targetAccount.setBalance(targetAccount.getBalance() + loanApplicationDTO.getAmount());
        accountService.saveAccount(targetAccount);

        return new ResponseEntity<>("Loan approved",HttpStatus.CREATED);

    }

}
