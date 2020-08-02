package com.mirocidij.simpleconsoleapplication;

import com.google.gson.Gson;
import com.mirocidij.simpleconsoleapplication.controllers.AccountController;
import com.mirocidij.simpleconsoleapplication.controllers.DeveloperController;
import com.mirocidij.simpleconsoleapplication.controllers.SkillController;
import com.mirocidij.simpleconsoleapplication.core.CLIManager;
import com.mirocidij.simpleconsoleapplication.core.ICLIManager;
import com.mirocidij.simpleconsoleapplication.repositories.JavaIOAccountRepository;
import com.mirocidij.simpleconsoleapplication.repositories.JavaIODeveloperRepository;
import com.mirocidij.simpleconsoleapplication.repositories.JavaIOSkillRepository;
import com.mirocidij.simpleconsoleapplication.views.AccountView;
import com.mirocidij.simpleconsoleapplication.views.DeveloperView;
import com.mirocidij.simpleconsoleapplication.views.SkillView;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Gson gson = new Gson();
        // repo
        JavaIOSkillRepository skillRepository = new JavaIOSkillRepository(gson, "skills.txt");
        JavaIOAccountRepository accountRepository = new JavaIOAccountRepository(
            gson, "accounts.txt");
        JavaIODeveloperRepository developerRepository = new JavaIODeveloperRepository(
            gson, "developers.txt");
        // controllers
        SkillController skillController = new SkillController(skillRepository);
        AccountController accountController = new AccountController(accountRepository);
        DeveloperController developerController = new DeveloperController(developerRepository);
        // views
        SkillView skillView = new SkillView(skillController);
        AccountView accountView = new AccountView(accountController);
        DeveloperView developerView = new DeveloperView(developerController);

        ICLIManager icliManager = new CLIManager(skillView, accountView, developerView);
        icliManager.start();
    }
}
