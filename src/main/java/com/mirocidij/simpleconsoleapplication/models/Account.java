package main.java.com.mirocidij.simpleconsoleapplication.models;

import main.java.com.mirocidij.simpleconsoleapplication.generic.entity.Entity;

public class Account extends Entity<Long> {
    private final AccountStatus accountStatus = AccountStatus.ACTIVE;
}