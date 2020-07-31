package com.mirocidij.simpleconsoleapplication;

import com.google.gson.Gson;
import com.mirocidij.simpleconsoleapplication.models.Skill;
import com.mirocidij.simpleconsoleapplication.repositories.SkillRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Startup {
    public static void main(String[] args) throws IOException {
        Gson gson = new Gson();

        SkillRepository skillRepository = new SkillRepository(gson);

        try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {
            String command;

            do {
                command = in.readLine();

                switch (command) {
                    case "1":
                        var skills = skillRepository.getAll();
                        skills.forEach(System.out::println);
                        break;
                    case "2":
                        System.out.print("Skill name >> ");
                        var skillName = in.readLine();
                        var skill = new Skill(skillName);
                        skillRepository.save(skill);
                        break;
                }

            } while (!command.equals("q"));
        }
    }
}
