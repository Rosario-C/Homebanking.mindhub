package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dto.AccountDTO;
import com.mindhub.homebanking.models.Account;

import java.util.Set;

public interface AccountService {

    Account getAccount(Long id);
    Account findByNumber(String number);
    Set<AccountDTO> getAccountsDTO();

    AccountDTO getAccountDTO(Long id);

    void saveAccount(Account account);


}