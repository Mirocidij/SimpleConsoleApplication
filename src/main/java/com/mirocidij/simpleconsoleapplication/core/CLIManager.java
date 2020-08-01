package com.mirocidij.simpleconsoleapplication.core;

import com.mirocidij.simpleconsoleapplication.views.AccountView;
import com.mirocidij.simpleconsoleapplication.views.DeveloperView;
import com.mirocidij.simpleconsoleapplication.views.GeneralView;
import com.mirocidij.simpleconsoleapplication.views.SkillView;
import com.mirocidij.simpleconsoleapplication.views.general.AbstractView;
import com.mirocidij.simpleconsoleapplication.views.general.IView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CLIManager implements ICLIManager {
    public final GeneralView generalView = new GeneralView();
    public final SkillView skillView;
    public final AccountView accountView;
    public final DeveloperView developerView;
    // State name
    public static final int SKILL_VIEW = 615;
    public static final int ACCOUNT_VIEW = 179;
    public static final int DEVELOPER_VIEW = 886;
    // Current state
    private IView state;

    public CLIManager(
        SkillView skillView,
        AccountView accountView,
        DeveloperView developerView
    ) {
        if (
            skillView == null
                || accountView == null
                || developerView == null
        ) {
            throw new IllegalArgumentException();
        }

        this.skillView = skillView;
        this.accountView = accountView;
        this.developerView = developerView;
        init();
    }

    public void init() {
        generalView.init(this);
        skillView.init(this);
        accountView.init(this);
        developerView.init(this);
    }

    @Override
    public void switchState(int stateNumber) {
        var state = switch (stateNumber) {
            case SKILL_VIEW -> skillView;
            case ACCOUNT_VIEW -> accountView;
            case DEVELOPER_VIEW -> developerView;
            default -> generalView;
        };

        setState(state);
    }

    @Override
    public void resetState() {
        setState(generalView);
    }

    @Override
    public void work() throws IOException {
        resetState();

        try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {
            String command;
            System.out.print("Command >> ");

            while (true) {
                command = in.readLine();
                state.process(command, in);
            }
        }
    }

    private void setState(AbstractView state) {
        state.showHelp();
        this.state = state;
    }
}
