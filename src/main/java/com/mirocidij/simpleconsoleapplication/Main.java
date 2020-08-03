package com.mirocidij.simpleconsoleapplication;

import com.mirocidij.simpleconsoleapplication.core.CLIManager;
import com.mirocidij.simpleconsoleapplication.core.ICLIManager;
import com.mirocidij.simpleconsoleapplication.views.ViewConfigurator;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ICLIManager icliManager = new CLIManager(
            ViewConfigurator.configure()
        );
        icliManager.start();
    }
}
