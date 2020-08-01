package com.mirocidij.simpleconsoleapplication.core;

import java.io.IOException;

public interface ICLIManager {
    void switchState(int state);

    void resetState();

    void start() throws IOException;
}
