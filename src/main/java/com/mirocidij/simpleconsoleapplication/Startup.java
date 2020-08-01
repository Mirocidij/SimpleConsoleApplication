package com.mirocidij.simpleconsoleapplication;

import com.google.gson.Gson;
import com.mirocidij.simpleconsoleapplication.models.Skill;
import com.mirocidij.simpleconsoleapplication.repositories.SkillRepository;
import com.mirocidij.simpleconsoleapplication.utils.ParseUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Startup {
    private static Gson gson;
    private static SkillRepository skillRepository;

    public static void main(String[] args) throws IOException {
        gson = new Gson();
        skillRepository = new SkillRepository(gson, "skills.txt");
        System.out.println(
            "1. show all skills\n2. add new skill\n3. delete skill\n4. update skill\n5. show skill by id\nq - to exit");

        try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {
            String command;

            do {
                System.out.print("Command >> ");
                command = in.readLine();

                switch (command) {
                    case "1" -> showAllSkills();
                    case "2" -> addNewSkill(in);
                    case "3" -> {
                        System.out.println("Delete mode");
                        Long skillId = getSkillId(in);
                        if (skillId == null) {
                            System.out.println("Incorrect id");
                        }
                        removeSkillById(skillId);
                    }
                    case "4" -> {
                        System.out.println("Edit mode");
                        Long skillId = getSkillId(in);
                        if (skillId == null) {
                            System.out.println("Skill not found");
                            continue;
                        }
                        var skillToEdit = skillRepository.getById(skillId);
                        if (skillToEdit == null) {
                            System.out.println("Skill not found");
                            break;
                        }
                        editSkill(in, skillToEdit);
                    }
                    case "5" -> {
                        System.out.println("Show mode");
                        var skillId = getSkillId(in);
                        var skill = skillRepository.getById(skillId);
                        if (skill != null)
                            System.out.println(skill);
                        else
                            System.out.println("Skill not found");
                    }
                }

            } while (!command.equals("q"));
        }
    }

    private static void editSkill(BufferedReader in, Skill skillToEdit) throws IOException {
        System.out.println("Current skill state: " + skillToEdit);
        System.out.print("New skill name >> ");
        var newSkillName = in.readLine();
        skillToEdit.setSkillName(newSkillName);
        skillRepository.update(skillToEdit);
        System.out.println("New skill state: " + skillToEdit);
    }

    public static void showAllSkills() {
        var skills = skillRepository.getAll();
        skills.forEach(System.out::println);
    }

    private static void addNewSkill(BufferedReader in) throws IOException {
        System.out.print("Skill name to add >> ");
        var skillName = in.readLine();
        var skill = new Skill(skillName);
        var addedSkill = skillRepository.save(skill);
        System.out.println("Was added: " + addedSkill);
    }

    private static Long getSkillId(BufferedReader in) throws IOException {
        System.out.print("Skill id >> ");
        return ParseUtils.TryParseLong(in.readLine());
    }

    private static void removeSkillById(Long skillId) {
        var skillToRemove = skillRepository.getById(skillId);
        var removed = skillRepository.deleteById(skillId);
        if (removed)
            System.out.println("Item removed completely: " + skillToRemove);
        else
            System.out.println("Item not found");
    }
}
