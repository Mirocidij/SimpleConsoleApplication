package com.mirocidij.simpleconsoleapplication;

import com.google.gson.Gson;
import com.mirocidij.simpleconsoleapplication.controllers.AccountController;
import com.mirocidij.simpleconsoleapplication.controllers.DeveloperController;
import com.mirocidij.simpleconsoleapplication.controllers.SkillController;
import com.mirocidij.simpleconsoleapplication.models.Skill;
import com.mirocidij.simpleconsoleapplication.repositories.AccountRepository;
import com.mirocidij.simpleconsoleapplication.repositories.DeveloperRepository;
import com.mirocidij.simpleconsoleapplication.repositories.SkillRepository;
import com.mirocidij.simpleconsoleapplication.utils.ParseUtils;
import com.mirocidij.simpleconsoleapplication.views.AccountView;
import com.mirocidij.simpleconsoleapplication.views.DeveloperView;
import com.mirocidij.simpleconsoleapplication.views.GeneralView;
import com.mirocidij.simpleconsoleapplication.views.SkillView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Startup {
    private static Gson gson;
    // repo
    private static SkillRepository skillRepository;
    private static AccountRepository accountRepository;
    private static DeveloperRepository developerRepository;
    // controllers
    private static SkillController skillController;
    private static AccountController accountController;
    private static DeveloperController developerController;
    // views
    private static SkillView skillView;
    private static AccountView accountView;
    private static DeveloperView developerView;

    private static final String menu =
        """
            1. show all skills
            2. add new skill
            3. delete skill
            4. update skill
            5. show skill by id
            help - menu
            quit - to exit
            """;

    public static void main(String[] args) throws IOException {
        init();
        showMenu();

        try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {
            String command;

            while (true) {
                System.out.print("Command >> ");
                command = in.readLine();
                System.out.println();

                switch (command) {
                    case SkillView.SHOW_ALL -> showAllSkills();
                    case SkillView.ADD_NEW -> addNewSkill(in);
                    case SkillView.REMOVE -> removeSkill(in);
                    case SkillView.EDIT -> editSkill(in);
                    case SkillView.SHOW_BY_ID -> showSkillById(in);
                    case GeneralView.HELP -> showMenu();
                    case GeneralView.QUIT -> quit();
                    default -> System.out.println("Unknown command! You can try \"help\"");
                }
            }
        }
    }

    private static void init() {
        gson = new Gson();
        // repo
        skillRepository = new SkillRepository(gson, "skills.txt");
        accountRepository = new AccountRepository(gson, "accounts.txt");
        developerRepository = new DeveloperRepository(gson, "developers.txt");
        // controllers
        skillController = new SkillController(skillRepository);
        accountController = new AccountController(accountRepository);
        developerController = new DeveloperController(developerRepository);
        // view
        skillView = new SkillView(skillController);
        accountView = new AccountView(accountController);
        developerView = new DeveloperView(developerController);
    }

    private static void showSkillById(BufferedReader in) throws IOException {
        System.out.println("Show mode");
        var skillId = getSkillId(in);
        var skill = skillRepository.getById(skillId);
        if (skill != null)
            System.out.println(skill);
        else
            System.out.println("Skill not found");
    }

    private static void editSkill(BufferedReader in) throws IOException {
        System.out.println("Edit mode");
        Long skillId = getSkillId(in);
        if (skillId == null) {
            System.out.println("Incorrect id");
            return;
        }
        var skillToEdit = skillRepository.getById(skillId);
        if (skillToEdit == null) {
            System.out.println("Skill not found");
            return;
        }
        editSkill(in, skillToEdit);
    }

    private static void removeSkill(BufferedReader in) throws IOException {
        System.out.println("Delete mode");
        Long skillId = getSkillId(in);
        if (skillId == null) {
            System.out.println("Incorrect id");
        }
        removeSkillById(skillId);
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

    private static void quit() {
        System.out.println("Good bye!");
        System.exit(0);
    }

    private static void showMenu() {
        System.out.println(menu);
    }
}
