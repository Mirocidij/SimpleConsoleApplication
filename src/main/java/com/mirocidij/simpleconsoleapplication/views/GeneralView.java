package com.mirocidij.simpleconsoleapplication.views;

import com.mirocidij.simpleconsoleapplication.core.CLIManager;
import com.mirocidij.simpleconsoleapplication.views.general.AbstractViewState;

public class GeneralView extends AbstractViewState {
    private final String menu =
        """
            1. skills menu
            2. accounts menu
            3. developers menu
            menu - menu
            quit - to exit
            """;

    // Commands
    public static final String SKILLS = "1";
    public static final String ACCOUNTS = "2";
    public static final String DEVELOPERS = "3";
    public static final String MENU = "menu";
    public static final String QUIT = "quit";

    public GeneralView() {
        showMenu();
    }

    @Override
    public void process(String command) {
        switch (command) {
            case SKILLS -> cliManager.switchState(CLIManager.SKILL_VIEW);
            case ACCOUNTS -> cliManager.switchState(CLIManager.ACCOUNT_VIEW);
            case DEVELOPERS -> cliManager.switchState(CLIManager.DEVELOPER_VIEW);
            case MENU -> showMenu();
            case QUIT -> quit();
        }
    }

    private void showMenu() {
        System.out.println(menu);
    }

    private void quit() {
        System.out.println("Good bye!");
        System.exit(0);
    }
}
