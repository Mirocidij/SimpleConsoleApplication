package com.mirocidij.simpleconsoleapplication.controllers;

import com.mirocidij.simpleconsoleapplication.models.Account;
import com.mirocidij.simpleconsoleapplication.models.AccountStatus;
import com.mirocidij.simpleconsoleapplication.repositories.AccountRepository;

import java.util.List;
import java.util.stream.Collectors;

public class AccountController {
    private final AccountRepository accountRepository;

    public AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> getAllAccounts() {
        return accountRepository.getAll();
    }

    public Account createAccount(String countryName) {
        var account = new Account(countryName);

        return accountRepository.save(account);
    }

    public Account getAccountById(Long id) {
        return accountRepository.getById(id);
    }

    public Account deleteById(Long id) {
        var accountToDelete = accountRepository.getById(id);
        var removed = accountRepository.deleteById(id);

        return removed ? accountToDelete : null;
    }

    public Account banAccountById(Long id) {
        var accountToBan = accountRepository.getById(id);

        if (accountToBan.getAccountStatus() == AccountStatus.DELETED) {
            return null;
        }
        if (accountToBan.getAccountStatus() == AccountStatus.BANNED) {
            return null;
        }

        accountToBan.setAccountStatus(AccountStatus.BANNED);
        accountRepository.update(accountToBan);

        return accountToBan;
    }

    public Account updateAccount(Account accountToUpdate) {
        return accountRepository.update(accountToUpdate);
    }

    public List<Account> findAccountCountryName(String countryName) {
        var accounts = accountRepository.getAll();

        return accounts
            .stream()
            .filter(x -> x.getAccountStatus() == AccountStatus.ACTIVE)
            .filter(x -> x.getCountryName().contains(countryName))
            .collect(Collectors.toList());
    }
}
