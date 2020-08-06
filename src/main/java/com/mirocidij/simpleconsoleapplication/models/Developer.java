package com.mirocidij.simpleconsoleapplication.models;

import com.google.gson.annotations.JsonAdapter;
import com.mirocidij.simpleconsoleapplication.generic.entity.Entity;
import com.mirocidij.simpleconsoleapplication.repositories.developer.gson.AccountAdapter;
import com.mirocidij.simpleconsoleapplication.repositories.developer.gson.SkillAdapter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
@Data
public class Developer extends Entity<Long> {
    @JsonAdapter(AccountAdapter.class)
    private Account account;
    @JsonAdapter(SkillAdapter.class)
    private Set<Skill> skills = new HashSet<>();
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    private String phoneNumber;

    public Developer() {}

    @Override
    public String toString() {
        return "Developer{" +
            "skills=" + skills +
            ", account=" + account +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", phoneNumber='" + phoneNumber + '\'' +
            ", id=" + getId() +
            '}';
    }
}
