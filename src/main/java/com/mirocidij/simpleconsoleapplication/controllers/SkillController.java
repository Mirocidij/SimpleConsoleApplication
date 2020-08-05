package com.mirocidij.simpleconsoleapplication.controllers;

import com.mirocidij.simpleconsoleapplication.models.Skill;
import com.mirocidij.simpleconsoleapplication.repositories.skill.SkillRepository;

import java.util.List;
import java.util.stream.Collectors;

public class SkillController {
    private final SkillRepository skillRepository;

    public SkillController(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public List<Skill> getAllSkills() {
        return skillRepository.getAll();
    }

    public Skill createSkill(String skillName) {
        var skill = new Skill(skillName);

        return skillRepository.save(skill);
    }

    public Skill getSkillById(Long skillId) {
        return skillRepository.getById(skillId);
    }

    public boolean deleteById(Long skillId) {
        return skillRepository.deleteById(skillId);
    }

    public Skill updateSkill(Skill skillToUpdate) {
        return skillRepository.update(skillToUpdate);
    }

    public List<Skill> findSkillByName(String skillName) {
        var skills = skillRepository.getAll();

        return skills
            .stream()
            .filter(x -> x.getName().contains(skillName))
            .collect(Collectors.toList());
    }
}
