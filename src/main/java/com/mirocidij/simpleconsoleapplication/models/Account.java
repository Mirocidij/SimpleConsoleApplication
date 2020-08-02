package com.mirocidij.simpleconsoleapplication.models;

import com.mirocidij.simpleconsoleapplication.generic.entity.Entity;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public class Account extends Entity<Long> {
    @Getter
    @Setter
    private AccountStatus accountStatus = AccountStatus.ACTIVE;
    @Getter
    @Setter
    @NonNull
    private String countryName;

    @Override
    public String toString() {
        return "Account{" +
            "accountStatus=" + accountStatus +
            ", countryName='" + countryName + '\'' +
            ", id=" + id +
            '}';
    }
}