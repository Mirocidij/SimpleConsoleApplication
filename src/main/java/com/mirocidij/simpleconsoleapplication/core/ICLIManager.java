package com.mirocidij.simpleconsoleapplication.core;

import java.io.IOException;

public interface ICLIManager {
    void switchState(Class viewType);

    void resetState();

    void start() throws IOException;
}
