package com.mindhub.homebanking.dto;

import com.mindhub.homebanking.models.Account;


import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {

    private long id;

    private String number;

    private LocalDateTime createDate;

    private double balance;

    private Set<TransactionDTO> transaction = new HashSet<>();

    private boolean accountActive;



public AccountDTO(){ }




    public AccountDTO(Account account ){

    this.id = account.getId();
    this.number = account.getNumber();
    this.balance =account.getBalance();
    this.createDate = account.getCreateDate();
    this.transaction= account.getTransactions().stream().map(TransactionDTO::new).collect(Collectors.toSet());
    this.accountActive = account.isAccountActive();
    }

    public long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Set<TransactionDTO> getTransactions() {
        return transaction;
    }

    public void setTransactions(Set<TransactionDTO> transactions) {
        this.transaction = transactions;
    }
    public boolean isAccountActive() {
        return accountActive;
    }

    public void setAccountActive(boolean accountActive) {
        this.accountActive = accountActive;
    }
}
