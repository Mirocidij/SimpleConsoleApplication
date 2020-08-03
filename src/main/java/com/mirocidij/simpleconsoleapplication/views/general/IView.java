package com.mirocidij.simpleconsoleapplication.views.general;

import java.io.BufferedReader;
import java.io.IOException;

public interface IView {
    void process(String command, BufferedReader in) throws IOException;

    void showHelp();

    void setOnCloseListener(ViewOnClose onCloseListener);

    void setOnViewChangeListener(ViewOnChangeView onViewChangeListener);
}
