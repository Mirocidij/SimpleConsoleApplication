package com.mirocidij.simpleconsoleapplication.controllers;

import com.mirocidij.simpleconsoleapplication.repositories.DeveloperRepository;

public class DeveloperController {
    private final DeveloperRepository developerRepository;

    public DeveloperController(DeveloperRepository developerRepository) {

        this.developerRepository = developerRepository;
    }
}
