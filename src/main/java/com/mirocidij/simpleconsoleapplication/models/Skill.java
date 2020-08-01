package com.mirocidij.simpleconsoleapplication.models;

import com.mirocidij.simpleconsoleapplication.generic.entity.Entity;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public class Skill extends Entity<Long> {
    @Getter
    @Setter
    @NonNull
    private String skillName;

    @Override
    public String toString() {
        return "Skill{" +
            "skillName='" + skillName + '\'' +
            ", id=" + id +
            '}';
    }
}