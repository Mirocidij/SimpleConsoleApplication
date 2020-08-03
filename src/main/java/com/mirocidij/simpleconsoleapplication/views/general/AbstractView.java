package com.mirocidij.simpleconsoleapplication.views.general;

import java.io.BufferedReader;
import java.io.IOException;

public abstract class AbstractView implements IView {
    public static final String HELP = "help";
    public static final String BACK = "back";
    public static final String QUIT = "quit";
    protected ViewOnClose onCloseListener = () ->
        System.out.println("On close default implementation " + this.getClass().getSimpleName());
    protected ViewOnChangeView onViewChangeListener = viewClass ->
        System.out.println(
            "On view change default implementation " + this.getClass().getSimpleName());

    protected String help =
        """
            help - help
            back - to main menu
            quit - quit
            """;

    @Override
    public void showHelp() {
        System.out.print(help);
    }

    private void showUnknownCommand() {
        System.out.println("Unknown command");
    }

    @Override
    public void process(String command, BufferedReader in) throws IOException {
        switch (command) {
            case HELP -> showHelp();
            case BACK -> onCloseListener.close();
            case QUIT -> quit();
            default -> showUnknownCommand();
        }
    }

    @Override
    public void setOnCloseListener(ViewOnClose onCloseListener) {
        this.onCloseListener = onCloseListener;
    }

    @Override
    public void setOnViewChangeListener(ViewOnChangeView onViewChangeListener) {
        this.onViewChangeListener = onViewChangeListener;
    }

    private void quit() {
        System.out.println("Good bye!");
        System.exit(0);
    }
}
