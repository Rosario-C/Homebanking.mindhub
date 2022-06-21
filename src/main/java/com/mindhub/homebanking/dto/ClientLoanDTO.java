package com.mindhub.homebanking.dto;

import com.mindhub.homebanking.models.ClientLoan;

public class ClientLoanDTO {
    private long id;

    private long idLoan;

    private String name;

    private double amount;

    private int payments;


    public ClientLoanDTO(){}

    public ClientLoanDTO(ClientLoan clientLoan){
        this.id = clientLoan.getId();
        this.idLoan = clientLoan.getLoan().getId();
        this.name = clientLoan.getLoan().getName();
        this.amount = clientLoan.getAmount();
        this.payments = clientLoan.getPayment();
    }

    public long getId() {
        return id;
    }

    public long getIdLoan() {
        return idLoan;
    }

    public void setIdLoan(long idLoan) {
        this.idLoan = idLoan;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getPayments() {
        return payments;
    }

    public void setPayments(int payments) {
        this.payments = payments;
    }
}

