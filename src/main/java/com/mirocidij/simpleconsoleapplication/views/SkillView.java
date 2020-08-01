package com.mirocidij.simpleconsoleapplication.views;

import com.mirocidij.simpleconsoleapplication.controllers.SkillController;
import com.mirocidij.simpleconsoleapplication.views.general.AbstractViewState;

public class SkillView extends AbstractViewState {
    public static final String SHOW_ALL = "1";
    public static final String ADD_NEW = "2";
    public static final String REMOVE = "3";
    public static final String EDIT = "4";
    public static final String SHOW_BY_ID = "5";
    private final SkillController skillController;

    public SkillView(SkillController skillController) {
        this.skillController = skillController;
    }

    @Override
    public void process(String command) {

    }
}
