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
import java.util.stream.Collectors;

public class SkillRepository implements GenericRepository<Skill, Long> {
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
        return getData();
    }

    @Override
    public Skill getById(Long id) {
        try {
            var skills = getData();

            return skills
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
        var skills = getData();

        long maxId = skills
            .stream()
            .map(Entity::getId)
            .max(Long::compare)
            .get();

        skill.setId(maxId + 1);

        skills.add(skill);
        saveChanges(skills);

        return skill;
    }

    @Override
    public Skill update(Skill skill) {
        var skills = getData();

        var skillToUpdate = skills
            .stream()
            .filter(x -> x.getId().equals(skill.getId()))
            .findFirst()
            .get();

        skillToUpdate.setSkillName(skill.getSkillName());

        saveChanges(skills);

        return skillToUpdate;
    }

    @Override
    public boolean deleteById(Long id) {
        var skills = getData();

        var removed = skills.removeIf(skill -> skill.getId().equals(id));

        if (removed) {
            saveChanges(skills);
        }

        return removed;
    }

    private List<Skill> getData() {
        var result = new ArrayList<Skill>();

        try (BufferedReader in = Files.newBufferedReader(path)) {
            var lines = in.lines().collect(Collectors.toList());
            var skills = gson.fromJson(in, Skill.class);
            for (String line : lines) {
                var skill = gson.fromJson(line, Skill.class);
                if (skill != null) result.add(skill);
            }
        } catch (NoSuchFileException e) {
            System.out.println("Missing file " + path.getFileName() + ": " + e);
        } catch (IOException e) {
            System.out.println("I/O Error");
            e.printStackTrace();
        }

        return result;
    }

    private void saveChanges(List<Skill> skillsToSave) {
        try (BufferedWriter out = new BufferedWriter(new FileWriter(filePath))) {
            String json;
            for (Skill skill : skillsToSave) {
                json = gson.toJson(skill) + '\n';
                out.write(json);
            }
        } catch (IOException e) {
            System.out.println("I/O Error: " + e);
        }
    }
}