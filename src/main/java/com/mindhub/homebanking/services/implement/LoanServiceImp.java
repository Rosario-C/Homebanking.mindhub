package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dto.ClientDTO;
import com.mindhub.homebanking.dto.ClientLoanDTO;
import com.mindhub.homebanking.dto.LoanApplicationDTO;
import com.mindhub.homebanking.dto.LoanDTO;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.*;
import com.mindhub.homebanking.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Service
public class LoanServiceImp implements LoanService {
    @Autowired
    public LoanRepository loanRepository;


    @Override
    public Set<LoanDTO> getLoansDTO() {
        return loanRepository.findAll().stream().map(loan -> new LoanDTO(loan)).collect(toSet());
    }

    @Override
    public Loan getLoan(Long id) {
        return loanRepository.findById(id).orElse(null);
    }

    @Override
    public Set<Loan> getLoans() {
        return loanRepository.findAll().stream().collect(toSet());
    }


    @Override
    public void saveLoan(Loan loan) {
        loanRepository.save(loan);
    }
}
