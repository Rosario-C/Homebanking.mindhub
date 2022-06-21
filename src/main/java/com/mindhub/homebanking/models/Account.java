package com.mindhub.homebanking.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client owner;

    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
     private Set<Transaction> transactions = new HashSet<>();

    private String number;

    private LocalDateTime createDate;

    private double balance;

    private boolean accountActive;

    public Account() { }



    public Account(Client client_id, String number, LocalDateTime createDate, double balance, boolean accountActive) {
        this.owner = client_id;
        this.number = number;
        this.createDate = createDate;
        this.balance = balance;

        this.accountActive = accountActive;
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

    public double getBalance(){
        return balance;
    }

    public void setBalance(double balance){
        this.balance = balance;
    }


    @JsonIgnore
    public Client getOwner() {
        return owner;
    }

    public void setOwner(Client client_id) {
        this.owner = client_id;
    }

    public long getId() {
        return id;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void addTransaction(Transaction transaction) {
        transaction.setAccount(this);
        transactions.add(transaction);
    }
    public boolean isAccountActive() {
        return accountActive;
    }

    public void setAccountActive(boolean accountActive) {
        this.accountActive = accountActive;
    }

    public String toString() {return number + " " + createDate + " " + balance;}

}
