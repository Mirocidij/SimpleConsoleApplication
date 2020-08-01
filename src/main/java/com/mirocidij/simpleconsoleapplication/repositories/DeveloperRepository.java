package com.mirocidij.simpleconsoleapplication.repositories;

import com.google.gson.Gson;
import com.mirocidij.simpleconsoleapplication.generic.repository.GenericRepository;
import com.mirocidij.simpleconsoleapplication.models.Developer;
import com.mirocidij.simpleconsoleapplication.utils.PathBuilder;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class DeveloperRepository implements GenericRepository<Developer, Long> {
    private final Path path;
    private final String filePath;
    private final Gson gson;

    public DeveloperRepository(Gson gson, String fileName) {
        this.gson = gson;
        this.filePath = PathBuilder.buildPath(fileName);
        this.path = Paths.get(filePath);
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
