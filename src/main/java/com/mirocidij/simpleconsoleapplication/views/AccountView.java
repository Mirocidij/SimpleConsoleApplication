package com.mirocidij.simpleconsoleapplication.views;

import com.mirocidij.simpleconsoleapplication.controllers.AccountController;
import com.mirocidij.simpleconsoleapplication.utils.ParseUtils;
import com.mirocidij.simpleconsoleapplication.views.general.AbstractView;

import java.io.BufferedReader;
import java.io.IOException;

public class AccountView extends AbstractView {
    public static final String SHOW_ALL = "1";
    public static final String ADD_NEW = "2";
    public static final String REMOVE = "3";
    public static final String BAN = "4";
    public static final String EDIT = "5";
    public static final String SHOW_BY_ID = "6";
    public static final String SEARCH_BY_COUNTRY_NAME = "7";
    private final AccountController accountController;

    public AccountView(AccountController accountController) {
        this.accountController = accountController;

        help =
            """
                1 - show all accounts
                2 - add new account
                3 - remove account
                4 - ban account
                5 - edit account
                6 - show account by id
                7 - search account by country name
                """ + help;
    }

    @Override
    public void process(String command, BufferedReader in) throws IOException {
        switch (command) {
            case SHOW_ALL -> showAllAccounts();
            case ADD_NEW -> addNewAccount(in);
            case REMOVE -> removeAccount(in);
            case BAN -> banAccount(in);
            case EDIT -> editAccount(in);
            case SHOW_BY_ID -> showAccountById(in);
            case SEARCH_BY_COUNTRY_NAME -> searchAccountByCountryName(in);
            default -> super.process(command, in);
        }
    }

    private void showAllAccounts() {
        var accounts = accountController.getAllAccounts();
        accounts.forEach(System.out::println);
    }

    private void addNewAccount(BufferedReader in) throws IOException {
        System.out.print("Type county name to add >> ");
        var countryName = in.readLine();
        var account = accountController.createAccount(countryName);

        if (account != null)
            System.out.println("Was added: " + account);
        else
            System.out.println("Account creating error");
    }

    private void removeAccount(BufferedReader in) throws IOException {
        Long accountId = getAccountId(in);
        if (accountId == null) return;

        var removedAccount = accountController.deleteById(accountId);
        if (removedAccount != null)
            System.out.println("Account removed completely: " + removedAccount);
        else
            System.out.println("Account remove error");
    }

    private void banAccount(BufferedReader in) throws IOException {
        Long accountId = getAccountId(in);
        if (accountId == null) return;

        var bannedAccount = accountController.banAccountById(accountId);
        if (bannedAccount != null)
            System.out.println("Account banned completely: " + bannedAccount);
        else
            System.out.println("Account ban error");
    }

    private void editAccount(BufferedReader in) throws IOException {
        Long accountId = getAccountId(in);
        if (accountId == null) return;

        var accountToEdit = accountController.getAccountById(accountId);
        if (accountToEdit == null) {
            System.out.println("Account not found");
            return;
        }

        System.out.println("Current account state: " + accountToEdit);
        System.out.print("New account country name >> ");
        var newCountryName = in.readLine();
        accountToEdit.setCountryName(newCountryName);

        var updatedAccount = accountController.updateAccount(accountToEdit);
        System.out.println("New account state: " + updatedAccount);
    }

    private void showAccountById(BufferedReader in) throws IOException {
        var accountId = getAccountId(in);
        if (accountId == null) return;

        var account = accountController.getAccountById(accountId);
        if (account == null) {
            System.out.println("Account not found");
            return;
        }

        System.out.println(account);
    }

    private void searchAccountByCountryName(BufferedReader in) throws IOException {
        System.out.print("Country name to find account >> ");
        var countryName = in.readLine();
        var foundAccounts = accountController.findAccountCountryName(countryName);

        if (foundAccounts.size() == 0)
            System.out.println("No accounts was found");
        else
            foundAccounts.forEach(System.out::println);
    }

    // utils methods

    private Long getAccountId(BufferedReader in) throws IOException {
        System.out.print("Account id >> ");
        var id = ParseUtils.TryParseLong(in.readLine());

        if (id == null) {
            System.out.println("Incorrect id");
        }

        return id;
    }
}
