package com.mindhub.homebanking.models;

import com.mindhub.homebanking.dto.AccountDTO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Entity
public class Client {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @OneToMany(mappedBy="owner", fetch=FetchType.EAGER)
    private Set<Account> accounts = new HashSet<>();
    @OneToMany(mappedBy="client", fetch=FetchType.EAGER)
    private Set<ClientLoan> clientLoans = new HashSet<>();

    @OneToMany(mappedBy="client", fetch=FetchType.EAGER)
    private Set<Card> cards = new HashSet<>();

    private String firstName;
    private String lastName;
    private String email;

    private String password;//propiedad

    public Client() { }

    public Client(String firstName, String lastName, String mail, String password) { //declaro el metodo constructor, y lo uso con
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = mail;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void addAccounts(Account account) {
        account.setOwner(this);
        accounts.add(account);
    }

    public Set<ClientLoan> getClientLoan() {
        return clientLoans;
    }

    public void addClientLoan(ClientLoan clientLoan) {
       clientLoan.setClient(this);
       clientLoans.add(clientLoan);
    }

    public List<Loan> getLoans() {
        return clientLoans.stream().map(clientLoan -> clientLoan.getLoan()).collect(toList());
    }

    public Set<Card> getCards() {
        return cards;
    }

    public void addCards(Card card) {
        card.setClient(this);
        cards.add(card);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString() {
        return firstName + " " + lastName + " " + email;
    }

  public long  getId(){
        return id;
  }


}
