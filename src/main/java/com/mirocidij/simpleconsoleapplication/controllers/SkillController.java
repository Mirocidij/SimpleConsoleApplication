package com.mirocidij.simpleconsoleapplication.controllers;

import com.mirocidij.simpleconsoleapplication.models.Skill;
import com.mirocidij.simpleconsoleapplication.repositories.SkillRepository;

import java.util.List;

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

    public Skill updateSkill(Skill skillToEdit) {
        return skillRepository.update(skillToEdit);
    }
}
