package com.mirocidij.simpleconsoleapplication.views;

import com.mirocidij.simpleconsoleapplication.controllers.DeveloperController;
import com.mirocidij.simpleconsoleapplication.utils.ParseUtils;
import com.mirocidij.simpleconsoleapplication.views.general.AbstractView;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class DeveloperView extends AbstractView {
    public static final String SHOW_ALL = "1";
    public static final String CREATE_NEW = "2";
    public static final String ADD_SKILL = "3";
    public static final String ADD_SOME_SKILLS = "4";
    public static final String REMOVE = "5";
    public static final String BAN = "6";
    public static final String SEARCH_BY_SKILL = "7";
    public static final String SHOW_REMOVED = "8";
    public static final String SHOW_BANNED = "9";

    private final DeveloperController developerController;

    public DeveloperView(DeveloperController developerController) {
        this.developerController = developerController;

        help =
            """
                1 - show active all developers
                2 - create new developer
                3 - add skill to developer
                4 - add some skills to developer
                5 - remove developer
                6 - ban developer
                7 - search developer by skill id
                8 - show removed developers
                9 - show banned developers
                """ + help;
    }

    @Override
    public void process(String command, BufferedReader in) throws IOException {
        switch (command) {
            case SHOW_ALL -> showALlDevelopers(in);
            case CREATE_NEW -> createNewDeveloper(in);
            case ADD_SKILL -> addSkillToDeveloper(in);
            case ADD_SOME_SKILLS -> addSomeSkillToDeveloper(in);
            case REMOVE -> removeDeveloper(in);
            case BAN -> banDeveloper(in);
            case SEARCH_BY_SKILL -> searchDeveloperBySkillId(in);
            case SHOW_REMOVED -> showRemovedDevelopers(in);
            case SHOW_BANNED -> showBannedDevelopers(in);
            default -> super.process(command, in);
        }
    }

    private void showALlDevelopers(BufferedReader in) {
        var developers = developerController.getAllActiveDevelopers();

        if (developers != null && developers.size() == 0)
            System.out.println("Developers not found");

        developers.forEach(System.out::println);
    }

    private void createNewDeveloper(BufferedReader in) throws IOException {
        System.out.print("Type developers first name >> ");
        var firstName = in.readLine();
        System.out.print("Type developers last name >> ");
        var lastName = in.readLine();
        System.out.print("Type developers phone number >> ");
        var phoneNumber = in.readLine();

        var accounts = developerController.getFreeAccounts();

        if (accounts.size() == 0) {
            System.out.println("Free accounts not found, please, create account first");
            return;
        }

        System.out.println("==== Free account list ====");
        accounts.forEach(System.out::println);
        System.out.println();

        System.out.println("Change free account from list");
        var accountId = getId(in);
        if (accountId == null) return;

        var developer = developerController.createNewDeveloper(
            firstName, lastName, phoneNumber, accountId);
        if (developer == null)
            System.out.println("Creating error");
        else
            System.out.println("Creating successful " + developer);
    }

    private void addSkillToDeveloper(BufferedReader in) throws IOException {
        System.out.println("Type developer id");
        var developerId = getId(in);
        if (developerId == null) return;

        var skills = developerController.getSkills();
        if (skills.size() == 0) {
            System.out.println("Skills not found. Please, add skills first");
            return;
        }

        System.out.println("==== Skill list ====");
        skills.forEach(System.out::println);

        System.out.println("Type skill id from list to add");
        var skillId = getId(in);
        if (skillId == null) return;

        var response = developerController.addSkillToDeveloper(developerId, skillId);

        if (response == null)
            System.out.println("Update error");

        System.out.println("New state of developer: " + response);
    }

    private void addSomeSkillToDeveloper(BufferedReader in) throws IOException {
        System.out.println("Type developer id");
        var developerId = getId(in);
        if (developerId == null) return;

        var skills = developerController.getSkills();
        if (skills.size() == 0) {
            System.out.println("Skills not found. Please, add skills first");
            return;
        }

        System.out.println("==== Skill list ====");
        skills.forEach(System.out::println);

        System.out.println("Type skill ids from list to add all");
        var skillIds = Arrays
            .stream(in.readLine().split(" "))
            .map(ParseUtils::tryParseLong)
            .collect(Collectors.toList());
        if (skillIds.contains(null)) {
            System.out.println("Incorrect id was occurred");
            return;
        }

        var response = developerController.addSkillsToDeveloper(developerId, skillIds);
        if (response == null) {
            System.out.println("Update error");
            return;
        }

        System.out.println("New developer state: " + response);
    }

    private void removeDeveloper(BufferedReader in) throws IOException {
        System.out.println("Type developer id");
        var developerId = getId(in);
        if (developerId == null) return;

        var response = developerController.removeDeveloper(developerId);
        if (response)
            System.out.println("Developer with id: " + developerId + " removed");
        else
            System.out.println("Remove error");
    }

    private void banDeveloper(BufferedReader in) throws IOException {
        System.out.println("Type developer id");
        var developerId = getId(in);
        if (developerId == null) return;

        var response = developerController.banDeveloper(developerId);
        if (response)
            System.out.println("Developer with id: " + developerId + " banned");
        else
            System.out.println("Ban error");
    }

    private void searchDeveloperBySkillId(BufferedReader in) throws IOException {
        var skills = developerController.getSkills();

        System.out.println("==== Skill list ====");
        skills.forEach(System.out::println);

        System.out.println("Type skill id from list to add");
        var skillId = getId(in);
        if (skillId == null) return;

        var response = developerController.searchDevelopersBySkillId(skillId);
        if (response.size() == 0) {
            System.out.println("Developers not found");
            return;
        }

        response.forEach(System.out::println);
    }

    private void showRemovedDevelopers(BufferedReader in) {
        var response = developerController.getRemovedDevelopers();

        if (response.size() == 0) {
            System.out.println("Developers not found");
            return;
        }

        response.forEach(System.out::println);
    }

    private void showBannedDevelopers(BufferedReader in) {
        var response = developerController.getBannedDevelopers();

        if (response.size() == 0) {
            System.out.println("Developers not found");
            return;
        }

        response.forEach(System.out::println);
    }

    // utils methods

    private Long getId(BufferedReader in) throws IOException {
        System.out.print("Type id here >> ");
        var id = ParseUtils.tryParseLong(in.readLine());

        if (id == null) {
            System.out.println("Incorrect id");
        }

        return id;
    }
}
