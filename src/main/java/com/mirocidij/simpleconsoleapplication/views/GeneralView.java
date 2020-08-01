package com.mirocidij.simpleconsoleapplication.views;

import com.mirocidij.simpleconsoleapplication.core.CLIManager;
import com.mirocidij.simpleconsoleapplication.views.general.AbstractView;

import java.io.BufferedReader;
import java.io.IOException;

public class GeneralView extends AbstractView {
    // Commands
    public static final String SKILLS = "1";
    public static final String ACCOUNTS = "2";
    public static final String DEVELOPERS = "3";

    public GeneralView() {
        help =
            """
                1 - skills menu
                2 - accounts menu
                3 - developers menu
                """ + help;
    }

    @Override
    public void process(String command, BufferedReader in) throws IOException {
        switch (command) {
            case SKILLS -> cliManager.switchState(CLIManager.SKILL_VIEW);
            case ACCOUNTS -> cliManager.switchState(CLIManager.ACCOUNT_VIEW);
            case DEVELOPERS -> cliManager.switchState(CLIManager.DEVELOPER_VIEW);
            default -> super.process(command, in);
        }
    }
}
