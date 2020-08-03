package com.mirocidij.simpleconsoleapplication;

import com.mirocidij.simpleconsoleapplication.core.CLIManager;
import com.mirocidij.simpleconsoleapplication.views.ViewConfigurator;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        new CLIManager()
            .addViews(ViewConfigurator.getViews())
            .start();
    }
}
