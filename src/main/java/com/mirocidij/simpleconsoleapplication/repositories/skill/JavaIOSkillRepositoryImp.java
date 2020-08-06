package com.mirocidij.simpleconsoleapplication.repositories.skill;

import com.google.gson.Gson;
import com.mirocidij.simpleconsoleapplication.generic.entity.Entity;
import com.mirocidij.simpleconsoleapplication.models.Skill;
import com.mirocidij.simpleconsoleapplication.utils.EntityUtils;
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
import java.util.stream.Collectors;

public class JavaIOSkillRepositoryImp implements SkillRepository {
    private final Path path;
    private final String filePath;
    private final Gson gson;

    public JavaIOSkillRepositoryImp(Gson gson, String fileName) {
        this.gson = gson;
        this.filePath = PathBuilder.buildPath(fileName);
        this.path = Paths.get(filePath);
    }

    @Override
    public List<Skill> getAll() {
        return getSkillsFromFile();
    }

    @Override
    public Skill getById(Long id) {
        return EntityUtils.findById(id, getSkillsFromFile());
    }

    @Override
    public Skill save(Skill skill) {
        var skills = getSkillsFromFile();

        skill.setId(getNextId(skills));
        skills.add(skill);

        saveSkillsToFile(skills);

        return skill;
    }

    @Override
    public Skill update(Skill skill) {
        var skills = getSkillsFromFile();
        var skillToUpdate = EntityUtils.findById(skill.getId(), skills);

        skillToUpdate.setName(skill.getName());
        saveSkillsToFile(skills);
        return skillToUpdate;
    }

    @Override
    public boolean deleteById(Long id) {
        var skills = getSkillsFromFile();

        var removed = skills.removeIf(skill -> skill.getId().equals(id));

        if (removed) {
            saveSkillsToFile(skills);
        }

        return removed;
    }

    private Long getNextId(List<Skill> skills) {
        return skills
            .stream()
            .map(Entity::getId)
            .max(Long::compare)
            .orElse(0L) + 1;
    }

    private List<Skill> getSkillsFromFile() {
        var result = new ArrayList<Skill>();

        try (BufferedReader in = Files.newBufferedReader(path)) {
            var lines = in.lines().collect(Collectors.toList());
            for (String line : lines) {
                var data = gson.fromJson(line, Skill.class);
                if (data != null) result.add(data);
            }
        } catch (NoSuchFileException e) {
            System.out.println("Missing file " + path.getFileName() + ": " + e);
        } catch (IOException e) {
            System.out.println("I/O Error");
            e.printStackTrace();
        }

        return result;
    }

    private void saveSkillsToFile(List<Skill> skillList) {
        try (BufferedWriter out = new BufferedWriter(new FileWriter(filePath))) {
            String json;
            for (Skill data : skillList) {
                json = gson.toJson(data) + '\n';
                out.write(json);
            }
        } catch (IOException e) {
            System.out.println("I/O Error: " + e);
        }
    }
}