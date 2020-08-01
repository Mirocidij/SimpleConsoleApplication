package com.mirocidij.simpleconsoleapplication.repositories;

import com.google.gson.Gson;
import com.mirocidij.simpleconsoleapplication.generic.repository.GenericRepository;
import com.mirocidij.simpleconsoleapplication.models.Account;
import com.mirocidij.simpleconsoleapplication.utils.PathBuilder;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class AccountRepository implements GenericRepository<Account, Long> {
    private final Path path;
    private final String filePath;
    private final Gson gson;

    public AccountRepository(Gson gson, String fileName) {
        this.gson = gson;
        this.filePath = PathBuilder.buildPath(fileName);
        this.path = Paths.get(filePath);
    }

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
