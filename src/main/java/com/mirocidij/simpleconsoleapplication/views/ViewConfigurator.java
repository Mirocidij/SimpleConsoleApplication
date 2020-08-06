package com.mirocidij.simpleconsoleapplication.views;

import com.google.gson.Gson;
import com.mirocidij.simpleconsoleapplication.controllers.AccountController;
import com.mirocidij.simpleconsoleapplication.controllers.DeveloperController;
import com.mirocidij.simpleconsoleapplication.controllers.SkillController;
import com.mirocidij.simpleconsoleapplication.repositories.account.JavaIOAccountRepositoryImpl;
import com.mirocidij.simpleconsoleapplication.repositories.developer.JavaIODeveloperRepositoryImpl;
import com.mirocidij.simpleconsoleapplication.repositories.skill.JavaIOSkillRepositoryImp;
import com.mirocidij.simpleconsoleapplication.views.general.IView;

public class ViewConfigurator {
    public static IView[] getViews() {
        Gson gson = new Gson();
        // repo
        JavaIOSkillRepositoryImp skillRepository = new JavaIOSkillRepositoryImp(gson, "skills.txt");
        JavaIOAccountRepositoryImpl accountRepository = new JavaIOAccountRepositoryImpl(gson, "accounts.txt");
        JavaIODeveloperRepositoryImpl developerRepository =
            new JavaIODeveloperRepositoryImpl(gson, "developers.txt", skillRepository, accountRepository);
        // controllers
        SkillController skillController = new SkillController(skillRepository);
        AccountController accountController = new AccountController(accountRepository);
        DeveloperController developerController =
            new DeveloperController(developerRepository, skillRepository, accountRepository);
        // views
        SkillView skillView = new SkillView(skillController);
        AccountView accountView = new AccountView(accountController);
        DeveloperView developerView = new DeveloperView(developerController);

        return new IView[]{ skillView, accountView, developerView };
    }
}
