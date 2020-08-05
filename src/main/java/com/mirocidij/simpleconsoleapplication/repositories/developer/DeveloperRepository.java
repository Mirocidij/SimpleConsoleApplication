package com.mirocidij.simpleconsoleapplication.repositories.developer;

import com.google.gson.Gson;
import com.mirocidij.simpleconsoleapplication.generic.entity.Entity;
import com.mirocidij.simpleconsoleapplication.models.Developer;
import com.mirocidij.simpleconsoleapplication.models.Skill;
import com.mirocidij.simpleconsoleapplication.repositories.AbstractRepository;
import com.mirocidij.simpleconsoleapplication.repositories.account.AccountRepository;
import com.mirocidij.simpleconsoleapplication.repositories.skill.SkillRepository;
import com.mirocidij.simpleconsoleapplication.utils.EntityUtils;

import java.util.HashSet;
import java.util.List;

public class DeveloperRepository extends AbstractRepository<Developer, Long> {
    private final SkillRepository skillRepository;
    private final AccountRepository accountRepository;

    public DeveloperRepository(
        Gson gson, String fileName,
        SkillRepository skillRepository,
        AccountRepository accountRepository
    ) {
        super(gson, fileName, Developer.class);
        this.skillRepository = skillRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Developer> getAll() {
        var developers = getDataFromFile();
        developers.forEach(this::initDeveloper);

        return developers;
    }

    @Override
    public Developer getById(Long id) {
        var developer = EntityUtils.findById(id, getDataFromFile());

        initDeveloper(developer);

        return developer;
    }

    @Override
    public Developer save(Developer developer) {
        if (developer.getAccount().getId() == null) return null;

        var developers = getDataFromFile();
        developer.setId(getNextId(developers));
        developers.add(developer);

        saveDataToFile(developers);

        return developer;
    }

    @Override
    public Developer update(Developer developer) {
        var developers = getDataFromFile();
        var developerToUpdate = EntityUtils.findById(developer.getId(), developers);

        developerToUpdate.setSkills(developer.getSkills());
        developerToUpdate.setFirstName(developer.getFirstName());
        developerToUpdate.setLastName(developer.getLastName());
        developerToUpdate.setPhoneNumber(developer.getPhoneNumber());

        saveDataToFile(developers);

        return developerToUpdate;
    }

    @Override
    public boolean deleteById(Long id) {
        var developers = getDataFromFile();
        var developerToDelete = EntityUtils.findById(id, developers);

        if (developerToDelete.getAccount().getId() == null) return false;

        return accountRepository.deleteById(developerToDelete.getAccount().getId());
    }

    private long getNextId(List<Developer> developers) {
        return developers
            .stream()
            .map(Entity::getId)
            .max(Long::compare)
            .orElse(0L) + 1;
    }

    private void initDeveloper(Developer developer) {
        var newSkillList = new HashSet<Skill>();

        for (Skill iSkill : developer.getSkills()) {
            var skill = skillRepository.getById(iSkill.getId());

            if (skill == null) {
                developer.getSkills().add(Skill.unknownSkill);
                continue;
            }

            newSkillList.add(skill);
        }

        developer.setSkills(newSkillList);

        var account = accountRepository.getById(developer.getAccount().getId());
        developer.setAccount(account);
    }
}
