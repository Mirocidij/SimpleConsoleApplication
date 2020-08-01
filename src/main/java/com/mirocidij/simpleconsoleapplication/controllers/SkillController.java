package com.mirocidij.simpleconsoleapplication.controllers;

import com.mirocidij.simpleconsoleapplication.repositories.SkillRepository;

public class SkillController {
    private final SkillRepository skillRepository;

    public SkillController(SkillRepository skillRepository) {

        this.skillRepository = skillRepository;
    }
}
