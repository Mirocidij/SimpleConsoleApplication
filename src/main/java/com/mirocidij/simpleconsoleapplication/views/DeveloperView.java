package com.mirocidij.simpleconsoleapplication.views;

import com.mirocidij.simpleconsoleapplication.controllers.DeveloperController;
import com.mirocidij.simpleconsoleapplication.views.general.AbstractView;

import java.io.BufferedReader;
import java.io.IOException;

public class DeveloperView extends AbstractView {
    private final DeveloperController developerController;

    public DeveloperView(DeveloperController developerController) {

        this.developerController = developerController;
    }

    @Override
    public void process(String command, BufferedReader in) throws IOException {
        super.process(command, in);
    }
}
