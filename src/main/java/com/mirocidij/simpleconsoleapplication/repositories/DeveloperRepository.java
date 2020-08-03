package com.mirocidij.simpleconsoleapplication.repositories;

import com.google.gson.Gson;
import com.mirocidij.simpleconsoleapplication.models.Developer;

import java.util.List;

public class DeveloperRepository extends AbstractRepository<Developer, Long> {
    private final SkillRepository skillRepository;
    private final AccountRepository accountRepository;

    public DeveloperRepository(
        Gson gson, String fileName,
        SkillRepository skillRepository,
        AccountRepository accountRepository
    ) {
        super(gson, fileName, Developer.class);
        this.skillRepository = skillRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Developer> getAll() {
        return null;
    }

    @Override
    public Developer getById(Long id) {
        return null;
    }

    @Override
    public Developer save(Developer developer) {
        return null;
    }

    @Override
    public Developer update(Developer developer) {
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }
}
