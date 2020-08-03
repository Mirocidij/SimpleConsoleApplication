package com.mirocidij.simpleconsoleapplication.repositories;

import com.google.gson.Gson;
import com.mirocidij.simpleconsoleapplication.generic.entity.Entity;
import com.mirocidij.simpleconsoleapplication.models.Skill;
import com.mirocidij.simpleconsoleapplication.utils.EntityUtils;

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

        skill.setId(getNextId(skills));
        skills.add(skill);

        saveDataToFile(skills);

        return skill;
    }

    @Override
    public Skill update(Skill skill) {
        var skills = getDataFromFile();
        var skillToUpdate = EntityUtils.findById(skill.getId(), skills);

        skillToUpdate.setSkillName(skill.getSkillName());
        saveDataToFile(skills);
        return skillToUpdate;
    }

    @Override
    public boolean deleteById(Long id) {
        var skills = getDataFromFile();

        var removed = skills.removeIf(skill -> skill.getId().equals(id));

        if (removed) {
            saveDataToFile(skills);
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
}