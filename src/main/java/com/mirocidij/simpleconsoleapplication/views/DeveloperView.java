package com.mirocidij.simpleconsoleapplication.views;

import com.mirocidij.simpleconsoleapplication.controllers.DeveloperController;
import com.mirocidij.simpleconsoleapplication.views.general.AbstractViewState;

public class DeveloperView extends AbstractViewState {
    private final DeveloperController developerController;

    public DeveloperView(DeveloperController developerController) {

        this.developerController = developerController;
    }

    @Override
    public void process(String command) {

    }
}
