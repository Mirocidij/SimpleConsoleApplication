package com.mirocidij.simpleconsoleapplication.core;

import java.io.IOException;

public interface ICLIManager {
    void switchState(int state);

    void init();

    void resetState();

    void work() throws IOException;
}
