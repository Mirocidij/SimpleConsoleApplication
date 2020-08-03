package com.mirocidij.simpleconsoleapplication.core;

import com.mirocidij.simpleconsoleapplication.views.general.IView;

import java.io.IOException;

public interface ICLIManager {
    <T extends IView> void switchState(Class<T> viewType);

    void resetState();

    void start() throws IOException;
}
