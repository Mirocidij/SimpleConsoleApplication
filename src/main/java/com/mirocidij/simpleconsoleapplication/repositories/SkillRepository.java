package com.mirocidij.simpleconsoleapplication.repositories;

import com.google.gson.Gson;
import com.mirocidij.simpleconsoleapplication.generic.entity.Entity;
import com.mirocidij.simpleconsoleapplication.models.Skill;
import com.mirocidij.simpleconsoleapplication.utils.EntityUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class SkillRepository extends AbstractRepository<Skill, Long> {
    public SkillRepository(Gson gson, String fileName) {
        super(gson, fileName, Skill.class);
    }

    @Override
    public List<Skill> getAll() {
        return getDataFromFile();
    }

    @Override
    public Skill getById(Long id) {
        return EntityUtils.findById(id, getDataFromFile());
    }

    @Override
    public Skill save(Skill skill) {
        var skills = getDataFromFile();

        skill.setId(getNextSkillId(skills));
        skills.add(skill);

        saveSkillsToFile(skills);

        return skill;
    }

    @Override
    public Skill update(Skill skill) {
        var skills = getDataFromFile();
        var skillToUpdate = EntityUtils.findById(skill.getId(), skills);

        skillToUpdate.setSkillName(skill.getSkillName());
        saveSkillsToFile(skills);
        return skillToUpdate;
    }

    @Override
    public boolean deleteById(Long id) {
        var skills = getDataFromFile();

        var removed = skills.removeIf(skill -> skill.getId().equals(id));

        if (removed) {
            saveSkillsToFile(skills);
        }

        return removed;
    }

    private Long getNextSkillId(List<Skill> skills) {
        return skills
            .stream()
            .map(Entity::getId)
            .max(Long::compare)
            .orElse(0L) + 1;
    }

    private void saveSkillsToFile(List<Skill> skillsToSave) {
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