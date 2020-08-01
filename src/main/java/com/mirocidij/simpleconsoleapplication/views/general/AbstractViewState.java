package com.mirocidij.simpleconsoleapplication.views.general;

import com.mirocidij.simpleconsoleapplication.core.ICLIManager;

public abstract class AbstractViewState implements IViewState {
    protected ICLIManager cliManager;

    @Override
    public void init(ICLIManager cliManager) {
        this.cliManager = cliManager;
    }
}
