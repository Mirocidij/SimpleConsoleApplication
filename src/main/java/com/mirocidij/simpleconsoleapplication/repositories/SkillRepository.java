package com.mirocidij.simpleconsoleapplication.repositories;

import com.google.gson.Gson;
import com.mirocidij.simpleconsoleapplication.generic.entity.Entity;
import com.mirocidij.simpleconsoleapplication.generic.repository.GenericRepository;
import com.mirocidij.simpleconsoleapplication.models.Skill;

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

public class SkillRepository implements GenericRepository<Skill, Long> {
    private final List<Skill> skillList = new ArrayList<>();
    private final Path path;
    private final String filePath = "./skills.txt";
    private final Gson gson;

    public SkillRepository(Gson pGson) {
        gson = pGson;
        path = Paths.get(filePath);
    }

    @Override
    public List<Skill> getAll() {
        refreshData();

        return skillList;
    }

    @Override
    public Skill getById(Long id) {
        refreshData();

        return skillList
            .stream()
            .filter(x -> x.getId().equals(id))
            .findFirst()
            .get();
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

        deleteById(skill.getId());

        return save(skill);
    }

    @Override
    public void deleteById(Long id) {
        refreshData();

        skillList.removeIf(skill -> skill.getId().equals(id));

        saveChanges();
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