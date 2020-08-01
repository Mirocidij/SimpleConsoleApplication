package com.mirocidij.simpleconsoleapplication.repositories;

import com.google.gson.Gson;
import com.mirocidij.simpleconsoleapplication.generic.entity.Entity;
import com.mirocidij.simpleconsoleapplication.generic.repository.GenericRepository;
import com.mirocidij.simpleconsoleapplication.models.Skill;
import com.mirocidij.simpleconsoleapplication.utils.PathBuilder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class SkillRepository implements GenericRepository<Skill, Long> {
    private final List<Skill> skillList = new ArrayList<>();
    private final Path path;
    private final String filePath;
    private final Gson gson;

    public SkillRepository(Gson gson, String fileName) {
        this.gson = gson;
        this.filePath = PathBuilder.buildPath(fileName);
        this.path = Paths.get(filePath);
    }

    @Override
    public List<Skill> getAll() {
        refreshData();

        return skillList;
    }

    @Override
    public Skill getById(Long id) {
        try {
            refreshData();

            return skillList
                .stream()
                .filter(x -> x.getId().equals(id))
                .findFirst()
                .get();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    @Override
    public Skill save(Skill skill) {
        refreshData();

        long maxId = skillList
            .stream()
            .map(Entity::getId)
            .max(Long::compare)
            .get();

        skill.setId(maxId + 1);

        skillList.add(skill);
        saveChanges();

        return skill;
    }

    @Override
    public Skill update(Skill skill) {
        refreshData();

        var skillToUpdate = getById(skill.getId());
        skillToUpdate.setSkillName(skill.getSkillName());

        saveChanges();

        return skillToUpdate;
    }

    @Override
    public boolean deleteById(Long id) {
        refreshData();

        var removed = skillList.removeIf(skill -> skill.getId().equals(id));

        if (removed) {
            saveChanges();
        }

        return removed;
    }

    private void refreshData() {
        skillList.clear();

        try (BufferedReader in = Files.newBufferedReader(path)) {
            String json = "";
            while (json != null) {
                json = in.readLine();
                var skill = gson.fromJson(json, Skill.class);
                if (skill != null) skillList.add(skill);
            }
        } catch (NoSuchFileException e) {
            System.out.println("Missing file " + path.getFileName() + ": " + e);
        } catch (IOException e) {
            System.out.println("I/O Error");
            e.printStackTrace();
        }
    }

    private void saveChanges() {
        try (BufferedWriter out = new BufferedWriter(new FileWriter(filePath))) {
            String json;
            for (Skill skill : skillList) {
                json = gson.toJson(skill) + '\n';
                out.write(json);
            }
        } catch (IOException e) {
            System.out.println("I/O Error: " + e);
        }
    }
}