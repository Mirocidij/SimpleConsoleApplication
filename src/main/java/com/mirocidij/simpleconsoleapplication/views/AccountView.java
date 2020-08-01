package com.mirocidij.simpleconsoleapplication.views;

import com.mirocidij.simpleconsoleapplication.controllers.AccountController;
import com.mirocidij.simpleconsoleapplication.views.general.AbstractViewState;

public class AccountView extends AbstractViewState {
    private final AccountController accountController;

    public AccountView(AccountController accountController) {

        this.accountController = accountController;
    }

    @Override
    public void process(String command) {

    }
}
