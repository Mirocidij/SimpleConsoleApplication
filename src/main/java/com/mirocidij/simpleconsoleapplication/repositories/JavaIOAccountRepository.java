package com.mirocidij.simpleconsoleapplication.repositories;

import com.google.gson.Gson;
import com.mirocidij.simpleconsoleapplication.generic.entity.Entity;
import com.mirocidij.simpleconsoleapplication.generic.repository.GenericRepository;
import com.mirocidij.simpleconsoleapplication.models.Account;
import com.mirocidij.simpleconsoleapplication.models.AccountStatus;
import com.mirocidij.simpleconsoleapplication.utils.EntityUtils;
import com.mirocidij.simpleconsoleapplication.utils.PathBuilder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JavaIOAccountRepository implements GenericRepository<Account, Long> {
    private final Path path;
    private final String filePath;
    private final Gson gson;

    public JavaIOAccountRepository(Gson gson, String fileName) {
        this.gson = gson;
        this.filePath = PathBuilder.buildPath(fileName);
        this.path = Paths.get(filePath);
    }

    @Override
    public List<Account> getAll() {
        return getAccountsFromFile();
    }

    @Override
    public Account getById(Long id) {
        return EntityUtils.findById(id, getAccountsFromFile());
    }

    @Override
    public Account save(Account account) {
        var accounts = getAccountsFromFile();

        account.setId(getNextAccountId(accounts));
        accounts.add(account);

        saveAccountsToFile(accounts);

        return account;
    }

    @Override
    public Account update(Account account) {
        var accounts = getAccountsFromFile();
        var accountToUpdate = EntityUtils.findById(account.getId(), accounts);

        accountToUpdate.setAccountStatus(account.getAccountStatus());
        accountToUpdate.setCountryName(account.getCountryName());
        saveAccountsToFile(accounts);
        return account;
    }

    @Override
    public boolean deleteById(Long id) {
        var accounts = getAccountsFromFile();

        var account = EntityUtils.findById(id, accounts);
        account.setAccountStatus(AccountStatus.DELETED);

        saveAccountsToFile(accounts);

        return true;
    }

    private Long getNextAccountId(List<Account> accounts) {
        return accounts
            .stream()
            .map(Entity::getId)
            .max(Long::compare)
            .orElse(0L) + 1;
    }

    private List<Account> getAccountsFromFile() {
        var result = new ArrayList<Account>();

        try (BufferedReader in = Files.newBufferedReader(path)) {
            var lines = in.lines().collect(Collectors.toList());
            for (String line : lines) {
                var account = gson.fromJson(line, Account.class);
                if (account != null) result.add(account);
            }
        } catch (NoSuchFileException e) {
            System.out.println("Missing file " + path.getFileName() + ": " + e);
        } catch (IOException e) {
            System.out.println("I/O Error");
            e.printStackTrace();
        }

        return result;
    }

    private void saveAccountsToFile(List<Account> accountsToSave) {
        try (BufferedWriter out = new BufferedWriter(new FileWriter(filePath))) {
            String json;
            for (Account account : accountsToSave) {
                json = gson.toJson(account) + '\n';
                out.write(json);
            }
        } catch (IOException e) {
            System.out.println("I/O Error: " + e);
        }
    }
}
