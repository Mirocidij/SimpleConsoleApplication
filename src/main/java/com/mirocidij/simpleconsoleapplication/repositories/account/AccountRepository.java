package com.mirocidij.simpleconsoleapplication.repositories.account;

import com.google.gson.Gson;
import com.mirocidij.simpleconsoleapplication.generic.entity.Entity;
import com.mirocidij.simpleconsoleapplication.models.Account;
import com.mirocidij.simpleconsoleapplication.models.AccountStatus;
import com.mirocidij.simpleconsoleapplication.repositories.AbstractRepository;
import com.mirocidij.simpleconsoleapplication.utils.EntityUtils;

import java.util.List;

public class AccountRepository extends AbstractRepository<Account, Long> {
    public AccountRepository(Gson gson, String fileName) {
        super(gson, fileName, Account.class);
    }

    @Override
    public List<Account> getAll() {
        return getDataFromFile();
    }

    @Override
    public Account getById(Long id) {
        return EntityUtils.findById(id, getDataFromFile());
    }

    @Override
    public Account save(Account account) {
        var accounts = getDataFromFile();

        account.setId(getNextAccountId(accounts));
        accounts.add(account);

        saveDataToFile(accounts);

        return account;
    }

    @Override
    public Account update(Account account) {
        var accounts = getDataFromFile();
        var accountToUpdate = EntityUtils.findById(account.getId(), accounts);

        accountToUpdate.setAccountStatus(account.getAccountStatus());
        accountToUpdate.setCountryName(account.getCountryName());
        accountToUpdate.setFree(account.isFree());
        saveDataToFile(accounts);
        return account;
    }

    @Override
    public boolean deleteById(Long id) {
        var accounts = getDataFromFile();

        var account = EntityUtils.findById(id, accounts);
        account.setAccountStatus(AccountStatus.DELETED);

        saveDataToFile(accounts);

        return true;
    }

    private Long getNextAccountId(List<Account> accounts) {
        return accounts
            .stream()
            .map(Entity::getId)
            .max(Long::compare)
            .orElse(0L) + 1;
    }
}
