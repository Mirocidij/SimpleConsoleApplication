package com.mirocidij.simpleconsoleapplication.views;

import com.mirocidij.simpleconsoleapplication.controllers.SkillController;
import com.mirocidij.simpleconsoleapplication.utils.ParseUtils;
import com.mirocidij.simpleconsoleapplication.views.general.AbstractView;

import java.io.BufferedReader;
import java.io.IOException;

public class SkillView extends AbstractView {
    public static final String SHOW_ALL = "1";
    public static final String ADD_NEW = "2";
    public static final String REMOVE = "3";
    public static final String EDIT = "4";
    public static final String SHOW_BY_ID = "5";
    private final SkillController skillController;

    public SkillView(SkillController skillController) {
        this.skillController = skillController;

        help =
            """
                1 - show all skills
                2 - add new skill
                3 - remove skill
                4 - edit skill
                5 - show skill
                """ + help;
    }

    @Override
    public void process(String command, BufferedReader in) throws IOException {
        switch (command) {
            case SHOW_ALL -> showAllSkills();
            case ADD_NEW -> addNewSkill(in);
            case REMOVE -> removeSkill(in);
            case EDIT -> editSkill(in);
            case SHOW_BY_ID -> showSkillById(in);
            default -> super.process(command, in);
        }
    }

    private void showAllSkills() {
        var skills = skillController.getAllSkills();
        skills.forEach(System.out::println);
    }

    private void addNewSkill(BufferedReader in) throws IOException {
        System.out.print("Skill name to add >> ");
        var skillName = in.readLine();

        var skill = skillController.createSkill(skillName);

        if (skill != null)
            System.out.println("Was added: " + skill);
        else
            System.out.println("Skill creating error");
    }

    private void removeSkill(BufferedReader in) throws IOException {
        System.out.println("Delete mode");
        Long skillId = getSkillId(in);
        if (skillId == null) {
            System.out.println("Incorrect id");
        }
        removeSkillById(skillId);
    }

    private Long getSkillId(BufferedReader in) throws IOException {
        System.out.print("Skill id >> ");
        return ParseUtils.TryParseLong(in.readLine());
    }

    private void removeSkillById(Long skillId) {
        var skillToRemove = skillController.getSkillById(skillId);
        var removed = skillController.deleteById(skillId);
        if (removed)
            System.out.println("Item removed completely: " + skillToRemove);
        else
            System.out.println("Item not found");
    }

    private void editSkill(BufferedReader in) throws IOException {
        System.out.println("Edit mode");
        Long skillId = getSkillId(in);
        if (skillId == null) {
            System.out.println("Incorrect id");
            return;
        }
        var skillToEdit = skillController.getSkillById(skillId);
        if (skillToEdit == null) {
            System.out.println("Skill not found");
            return;
        }

        System.out.println("Current skill state: " + skillToEdit);
        System.out.print("New skill name >> ");
        var newSkillName = in.readLine();
        skillToEdit.setSkillName(newSkillName);
        skillController.updateSkill(skillToEdit);
        System.out.println("New skill state: " + skillToEdit);
    }

    private void showSkillById(BufferedReader in) throws IOException {
        System.out.println("Show mode");
        var skillId = getSkillId(in);
        var skill = skillController.getSkillById(skillId);
        if (skill != null)
            System.out.println(skill);
        else
            System.out.println("Skill not found");
    }
}
