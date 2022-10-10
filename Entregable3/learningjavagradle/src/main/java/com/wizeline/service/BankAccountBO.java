package com.wizeline.service;

import com.wizeline.model.BankAccountDTO;

import java.util.List;


public interface BankAccountBO {
    List<BankAccountDTO> getAccounts();
    BankAccountDTO getAccountDetails(String user, String lastUsage);
}
