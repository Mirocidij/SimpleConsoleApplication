package com.mirocidij.simpleconsoleapplication.controllers;

import com.mirocidij.simpleconsoleapplication.generic.entity.Entity;
import com.mirocidij.simpleconsoleapplication.models.Account;
import com.mirocidij.simpleconsoleapplication.models.AccountStatus;
import com.mirocidij.simpleconsoleapplication.models.Developer;
import com.mirocidij.simpleconsoleapplication.models.Skill;
import com.mirocidij.simpleconsoleapplication.repositories.account.JavaIOAccountRepositoryImpl;
import com.mirocidij.simpleconsoleapplication.repositories.developer.JavaIODeveloperRepositoryImpl;
import com.mirocidij.simpleconsoleapplication.repositories.skill.JavaIOSkillRepositoryImp;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DeveloperController {
    private final JavaIODeveloperRepositoryImpl developerRepository;
    private final JavaIOSkillRepositoryImp skillRepository;
    private final JavaIOAccountRepositoryImpl accountRepository;

    public DeveloperController(
        JavaIODeveloperRepositoryImpl developerRepository,
        JavaIOSkillRepositoryImp skillRepository,
        JavaIOAccountRepositoryImpl accountRepository
    ) {
        this.developerRepository = developerRepository;
        this.skillRepository = skillRepository;
        this.accountRepository = accountRepository;
    }

    public List<Developer> getAllActiveDevelopers() {
        return developerRepository.getAll()
                                  .stream()
                                  .filter(x -> x.getAccount().getAccountStatus() == AccountStatus.ACTIVE)
                                  .collect(Collectors.toList());
    }

    public List<Account> getFreeAccounts() {
        return accountRepository.getAll()
                                .stream()
                                .filter(Account::isFree)
                                .collect(Collectors.toList());
    }

    public Developer createNewDeveloper(
        String firstName,
        String lastName,
        String phoneNumber,
        Long accountId
    ) {
        var account = accountRepository.getById(accountId);
        if (account == null) return null;
        if (!account.isFree()) return null;
        account.setFree(false);

        var developer = new Developer(firstName, lastName, phoneNumber);
        developer.setAccount(account);

        accountRepository.update(account);
        return developerRepository.save(developer);
    }

    public List<Skill> getSkills() {
        return skillRepository.getAll();
    }

    public Developer addSkillToDeveloper(Long developerId, Long skillId) {
        var developer = developerRepository.getById(developerId);
        if (developer == null) return null;
        var skill = skillRepository.getById(skillId);
        if (skill == null) return null;

        var didNotContain = developer.getSkills().add(skill);
        developerRepository.update(developer);

        if (didNotContain)
            return developer;
        else
            return null;
    }

    public Developer addSkillsToDeveloper(Long developerId, List<Long> skillIds) {
        var developer = developerRepository.getById(developerId);
        if (developer == null) return null;
        var skills = new ArrayList<Skill>();
        for (Long skillId : skillIds) {
            var skill = skillRepository.getById(skillId);
            if (skill == null) return null;
            skills.add(skill);
        }

        developer.getSkills().addAll(skills);
        developerRepository.update(developer);

        return developer;
    }

    public boolean removeDeveloper(Long developerId) {
        return developerRepository.deleteById(developerId);
    }

    public boolean banDeveloper(Long developerId) {
        var developer = developerRepository.getById(developerId);
        if (developer == null) return false;

        var account = developer.getAccount();
        account.setAccountStatus(AccountStatus.BANNED);
        var result = accountRepository.update(account);

        return result != null;
    }

    public List<Developer> searchDevelopersBySkillId(Long skillId) {
        return developerRepository.getAll()
                                  .stream()
                                  .filter(x -> skillsContainSkillId(x, skillId))
                                  .collect(Collectors.toList());
    }

    public List<Developer> getRemovedDevelopers() {
        return developerRepository.getAll()
                                  .stream()
                                  .filter(x -> x.getAccount().getAccountStatus() == AccountStatus.DELETED)
                                  .collect(Collectors.toList());
    }

    public List<Developer> getBannedDevelopers() {
        return developerRepository.getAll()
                                  .stream()
                                  .filter(x -> x.getAccount().getAccountStatus() == AccountStatus.BANNED)
                                  .collect(Collectors.toList());
    }

    private boolean skillsContainSkillId(Developer developer, Long skillId) {
        var skillIds = developer.getSkills()
                                .stream()
                                .map(Entity::getId)
                                .collect(Collectors.toList());

        return skillIds.contains(skillId);
    }
}
