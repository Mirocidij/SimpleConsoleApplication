package com.mirocidij.simpleconsoleapplication.views.general;

import com.mirocidij.simpleconsoleapplication.core.ICLIManager;

import java.io.BufferedReader;
import java.io.IOException;

public abstract class AbstractView implements IView {
    public static final String HELP = "help";
    public static final String BACK = "back";
    public static final String QUIT = "quit";
    protected ICLIManager cliManager;

    protected String help =
        """
            help - help
            back - to main menu
            quit - exit
            """;

    @Override
    public void init(ICLIManager cliManager) {
        this.cliManager = cliManager;
    }

    @Override
    public void showHelp() {
        System.out.println(help);
    }

    private void showUnknownCommand() {
        System.out.println("Unknown command");
    }

    private void back() {
        cliManager.resetState();
    }

    private void quit() {
        System.out.println("Good bye!");
        System.exit(0);
    }

    @Override
    public void process(String command, BufferedReader in) throws IOException {
        switch (command) {
            case HELP -> showHelp();
            case BACK -> back();
            case QUIT -> quit();
            default -> showUnknownCommand();
        }
        showUnknownCommand();
    }
}
