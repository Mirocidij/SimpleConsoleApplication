package com.mirocidij.simpleconsoleapplication.views;

import com.mirocidij.simpleconsoleapplication.controllers.AccountController;
import com.mirocidij.simpleconsoleapplication.views.general.AbstractView;

import java.io.BufferedReader;
import java.io.IOException;

public class AccountView extends AbstractView {
    private final AccountController accountController;

    public AccountView(AccountController accountController) {

        this.accountController = accountController;
    }

    @Override
    public void process(String command, BufferedReader in) throws IOException {
        super.process(command, in);
    }
}
