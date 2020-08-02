package com.mirocidij.simpleconsoleapplication.models;

import com.mirocidij.simpleconsoleapplication.generic.entity.Entity;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class Developer extends Entity<Long> {
    @Getter
    @Setter
    private transient Set<Skill> skills = new HashSet<>();
    @Getter
    @Setter
    private List<Long> skillIds = new ArrayList<>();
    @Getter
    @Setter
    @NonNull
    private Account account;

    @Override
    public String toString() {
        return "Developer{" +
            "skills=" + skills +
            ", account=" + account +
            ", id=" + id +
            '}';
    }
}
