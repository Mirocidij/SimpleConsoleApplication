package com.mirocidij.simpleconsoleapplication.repositories;

import com.mirocidij.simpleconsoleapplication.generic.repository.GenericRepository;
import com.mirocidij.simpleconsoleapplication.models.Developer;

import java.util.List;

public class DeveloperRepository implements GenericRepository<Developer, Long> {
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
