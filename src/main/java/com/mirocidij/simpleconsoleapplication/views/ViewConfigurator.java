package com.mirocidij.simpleconsoleapplication.views;

import com.google.gson.Gson;
import com.mirocidij.simpleconsoleapplication.controllers.AccountController;
import com.mirocidij.simpleconsoleapplication.controllers.DeveloperController;
import com.mirocidij.simpleconsoleapplication.controllers.SkillController;
import com.mirocidij.simpleconsoleapplication.repositories.AccountRepository;
import com.mirocidij.simpleconsoleapplication.repositories.DeveloperRepository;
import com.mirocidij.simpleconsoleapplication.repositories.SkillRepository;
import com.mirocidij.simpleconsoleapplication.views.general.IView;

public class ViewConfigurator {
    public static IView[] getViews() {
        Gson gson = new Gson();
        // repo
        SkillRepository skillRepository = new SkillRepository(gson, "skills.txt");
        AccountRepository accountRepository = new AccountRepository(gson, "accounts.txt");
        DeveloperRepository developerRepository =
            new DeveloperRepository(gson, "developers.txt", skillRepository, accountRepository);
        // controllers
        SkillController skillController = new SkillController(skillRepository);
        AccountController accountController = new AccountController(accountRepository);
        DeveloperController developerController = new DeveloperController(developerRepository);
        // views
        SkillView skillView = new SkillView(skillController);
        AccountView accountView = new AccountView(accountController);
        DeveloperView developerView = new DeveloperView(developerController);

        return new IView[]{ skillView, accountView, developerView };
    }
}
