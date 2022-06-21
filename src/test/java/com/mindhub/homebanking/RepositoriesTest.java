package com.mindhub.homebanking;

import com.mindhub.homebanking.repositories.LoanRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RepositoriesTest {
    @Autowired
    LoanRepository loanRepository;
    @Test
    public void existLoans(){

    }
}
