package com.mindhub.homebanking.services;
import com.mindhub.homebanking.dto.LoanDTO;
import com.mindhub.homebanking.models.Loan;
import java.util.Set;

public interface LoanService {

    Set<LoanDTO> getLoansDTO();

    Loan getLoan(Long id);

    Set<Loan> getLoans();
    void saveLoan(Loan loan);

}