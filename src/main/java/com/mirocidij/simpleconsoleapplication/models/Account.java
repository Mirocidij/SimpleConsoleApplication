package com.mirocidij.simpleconsoleapplication.models;

import com.mirocidij.simpleconsoleapplication.generic.entity.Entity;

public class Account extends Entity<Long> {
    private final AccountStatus accountStatus = AccountStatus.ACTIVE;
}