package com.mirocidij.simpleconsoleapplication.views.general;

import com.mirocidij.simpleconsoleapplication.core.ICLIManager;

import java.io.BufferedReader;
import java.io.IOException;

public interface IView {
    void process(String command, BufferedReader in) throws IOException;

    void init(ICLIManager cliManager);

    void showHelp();
}
