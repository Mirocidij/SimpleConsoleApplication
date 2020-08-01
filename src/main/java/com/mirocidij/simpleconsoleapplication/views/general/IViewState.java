package com.mirocidij.simpleconsoleapplication.views.general;

import com.mirocidij.simpleconsoleapplication.core.ICLIManager;

public interface IViewState {
    void process(String command);

    void init(ICLIManager cliManager);
}
