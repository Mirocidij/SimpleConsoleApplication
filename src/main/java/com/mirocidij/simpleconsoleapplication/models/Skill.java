package com.mirocidij.simpleconsoleapplication.models;

import com.mirocidij.simpleconsoleapplication.generic.entity.Entity;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@RequiredArgsConstructor
public class Skill extends Entity<Long> {
    public static Skill unknownSkill = new Skill("UNKNOWN SKILL");

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

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
