package com.mirocidij.simpleconsoleapplication.models;

import com.mirocidij.simpleconsoleapplication.generic.entity.Entity;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
@Data
public class Skill extends Entity<Long> {
    public static Skill unknownSkill = new Skill("UNKNOWN SKILL");

    public Skill() {}

    @NonNull
    private String name;

    @Override
    public String toString() {
        return "Skill{" +
            "name='" + name + '\'' +
            ", id=" + getId() +
            '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
