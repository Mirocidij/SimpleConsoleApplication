package com.mirocidij.simpleconsoleapplication.controllers;

import com.mirocidij.simpleconsoleapplication.repositories.JavaIODeveloperRepository;

public class DeveloperController {
    private final JavaIODeveloperRepository developerRepository;

    public DeveloperController(JavaIODeveloperRepository developerRepository) {

        this.developerRepository = developerRepository;
    }
}
