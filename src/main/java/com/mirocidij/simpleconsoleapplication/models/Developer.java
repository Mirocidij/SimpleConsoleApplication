package main.java.com.mirocidij.simpleconsoleapplication.models;

import main.java.com.mirocidij.simpleconsoleapplication.generic.entity.Entity;

import java.util.HashSet;
import java.util.Set;

public class Developer extends Entity<Long> {
    private final Set<Skill> skills = new HashSet<>();
    private Account account;
}
