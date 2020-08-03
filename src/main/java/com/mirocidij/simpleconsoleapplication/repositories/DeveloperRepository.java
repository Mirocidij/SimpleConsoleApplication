package com.mirocidij.simpleconsoleapplication.repositories;

import com.google.gson.Gson;
import com.mirocidij.simpleconsoleapplication.generic.entity.Entity;
import com.mirocidij.simpleconsoleapplication.models.Developer;
import com.mirocidij.simpleconsoleapplication.utils.EntityUtils;

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

        for (Developer developer : developers) {
            for (Long skillId : developer.getSkillIds()) {
                var skill = skillRepository.getById(skillId);
                developer.getSkills().add(skill);
            }
        }

        return developers;
    }

    @Override
    public Developer getById(Long id) {
        return EntityUtils.findById(id, getDataFromFile());
    }

    @Override
    public Developer save(Developer developer) {
        if (developer.getAccountId() == null) return null;

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

        developerToUpdate.setSkillIds(developer.getSkillIds());
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

        if (developerToDelete.getAccountId() == null) return false;

        return accountRepository.deleteById(developerToDelete.getAccountId());
    }

    private long getNextId(List<Developer> developers) {
        return developers
            .stream()
            .map(Entity::getId)
            .max(Long::compare)
            .orElse(0L) + 1;
    }
}
