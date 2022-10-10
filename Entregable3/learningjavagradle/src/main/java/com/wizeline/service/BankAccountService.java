package com.wizeline.service;

import com.wizeline.model.BankAccountDTO;

import java.util.List;

public interface BankAccountService {


    List<BankAccountDTO> getAccounts();

    BankAccountDTO getAccountDetails(String user, String lastUsage);

    void deleteAccounts();

    List<BankAccountDTO> getAccountByUser(String user);

}
