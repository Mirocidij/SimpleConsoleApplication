package com.mirocidij.simpleconsoleapplication.repositories;

import com.mirocidij.simpleconsoleapplication.generic.repository.GenericRepository;
import com.mirocidij.simpleconsoleapplication.models.Account;

import java.util.List;

public class AccountRepository implements GenericRepository<Account, Long> {
    @Override
    public List<Account> getAll() {
        return null;
    }

    @Override
    public Account getById(Long id) {
        return null;
    }

    @Override
    public Account save(Account account) {
        return null;
    }

    @Override
    public Account update(Account account) {
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }
}
