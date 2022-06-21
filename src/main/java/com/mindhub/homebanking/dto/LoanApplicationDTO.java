package com.mindhub.homebanking.dto;

import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LoanApplicationDTO {

    private long loanId;
    private String borrowerAccount;
    private double amount;

    private int payments;

    public LoanApplicationDTO (){}

    public LoanApplicationDTO(long loanId, String borrowerAccount, double amount, int payments) {
        this.loanId = loanId;
        this.borrowerAccount = borrowerAccount;
        this.amount = amount;
        this.payments = payments;
    }

    public long getLoanId() {
        return loanId;
    }


    public String getBorrowerAccount() {
        return borrowerAccount;
    }



    public double getAmount() {
        return amount;
    }



    public int getPayments() {
        return payments;
    }


}
