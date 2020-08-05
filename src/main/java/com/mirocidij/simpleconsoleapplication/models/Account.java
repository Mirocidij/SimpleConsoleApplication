package com.mirocidij.simpleconsoleapplication.models;

import com.mirocidij.simpleconsoleapplication.generic.entity.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
@Data
public class Account extends Entity<Long> {
    public static Account unknownAccount = new Account("Unknown");

    public Account() { }

    private AccountStatus accountStatus = AccountStatus.ACTIVE;
    @NonNull
    private String countryName;
    private boolean isFree = true;

    @Override
    public String toString() {
        return "Account{" +
            "accountStatus=" + accountStatus +
            ", countryName='" + countryName + '\'' +
            ", isFree=" + isFree +
            ", id=" + getId() +
            '}';
    }
}