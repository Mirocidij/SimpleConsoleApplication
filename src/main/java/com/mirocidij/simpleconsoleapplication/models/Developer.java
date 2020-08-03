package com.mirocidij.simpleconsoleapplication.models;

import com.mirocidij.simpleconsoleapplication.generic.entity.Entity;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
public class Developer extends Entity<Long> {
    @Getter
    @Setter
    private Set<Skill> skills = new HashSet<>();
    @Getter
    @Setter
    private transient Account account;
    @Getter
    @Setter
    @NonNull
    private String firstName;
    @Getter
    @Setter
    @NonNull
    private String lastName;
    @Getter
    @Setter
    @NonNull
    private String phoneNumber;
    @Getter
    @Setter
    private Set<Long> skillIds = new HashSet<>();
    @Getter
    @Setter
    @NonNull
    private Long accountId;

    public Developer() {}

    @Override
    public String toString() {
        return "Developer{" +
            "skills=" + skills +
            ", account=" + account +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", phoneNumber='" + phoneNumber + '\'' +
            ", id=" + id +
            '}';
    }
}
