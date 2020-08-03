package com.mirocidij.simpleconsoleapplication.core;

import com.mirocidij.simpleconsoleapplication.views.GeneralView;
import com.mirocidij.simpleconsoleapplication.views.general.IView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class CLIManager implements ICLIManager {
    public final GeneralView generalView = new GeneralView();
    private IView state;
    private final HashMap<Class<? extends IView>, IView> viewResolver = new HashMap<>();

    public CLIManager addViews(IView... views) {
        if (
            Arrays.stream(views).anyMatch(Objects::isNull)
        ) {
            throw new IllegalArgumentException();
        }
        generalView.setOnViewChangeListener(this::switchState);
        viewResolver.put(GeneralView.class, generalView);

        for (IView view : views) {
            view.setOnCloseListener(this::resetState);
            viewResolver.put(view.getClass(), view);
        }

        return this;
    }

    @Override
    public void run() throws IOException {
        resetState();

        try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {
            String command;

            while (true) {
                System.out.println();
                System.out.print("Command >> ");
                command = in.readLine();
                System.out.println();
                state.process(command, in);
            }
        }
    }

    private <T extends IView> void switchState(Class<T> viewType) {
        if (viewResolver.containsKey(viewType)) {
            setState(viewResolver.get(viewType));
        }
    }

    private void resetState() {
        setState(generalView);
    }

    private void setState(IView state) {
        if (this.state != state) {
            state.showHelp();
            this.state = state;
        }
    }
}
