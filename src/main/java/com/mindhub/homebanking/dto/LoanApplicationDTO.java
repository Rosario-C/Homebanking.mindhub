package com.mindhub.homebanking.dto;



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
