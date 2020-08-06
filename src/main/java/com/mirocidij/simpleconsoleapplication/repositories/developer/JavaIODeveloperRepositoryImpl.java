package com.mirocidij.simpleconsoleapplication.repositories.developer;

import com.google.gson.Gson;
import com.mirocidij.simpleconsoleapplication.generic.entity.Entity;
import com.mirocidij.simpleconsoleapplication.models.Developer;
import com.mirocidij.simpleconsoleapplication.models.Skill;
import com.mirocidij.simpleconsoleapplication.repositories.account.JavaIOAccountRepositoryImpl;
import com.mirocidij.simpleconsoleapplication.repositories.skill.JavaIOSkillRepositoryImp;
import com.mirocidij.simpleconsoleapplication.utils.EntityUtils;
import com.mirocidij.simpleconsoleapplication.utils.PathBuilder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class JavaIODeveloperRepositoryImpl implements DeveloperRepository {
    private final Path path;
    private final String filePath;
    private final Gson gson;
    private final JavaIOSkillRepositoryImp skillRepository;
    private final JavaIOAccountRepositoryImpl accountRepository;

    public JavaIODeveloperRepositoryImpl(
        Gson gson, String fileName,
        JavaIOSkillRepositoryImp skillRepository,
        JavaIOAccountRepositoryImpl accountRepository
    ) {
        this.gson = gson;
        this.filePath = PathBuilder.buildPath(fileName);
        this.path = Paths.get(filePath);
        this.skillRepository = skillRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Developer> getAll() {
        var developers = getDevelopersFromFile();
        developers.forEach(this::initDeveloper);

        return developers;
    }

    @Override
    public Developer getById(Long id) {
        var developer = EntityUtils.findById(id, getDevelopersFromFile());

        initDeveloper(developer);

        return developer;
    }

    @Override
    public Developer save(Developer developer) {
        if (developer.getAccount().getId() == null) return null;

        var developers = getDevelopersFromFile();
        developer.setId(getNextId(developers));
        developers.add(developer);

        saveDevelopersToFile(developers);

        return developer;
    }

    @Override
    public Developer update(Developer developer) {
        var developers = getDevelopersFromFile();
        var developerToUpdate = EntityUtils.findById(developer.getId(), developers);

        developerToUpdate.setSkills(developer.getSkills());
        developerToUpdate.setFirstName(developer.getFirstName());
        developerToUpdate.setLastName(developer.getLastName());
        developerToUpdate.setPhoneNumber(developer.getPhoneNumber());

        saveDevelopersToFile(developers);

        return developerToUpdate;
    }

    @Override
    public boolean deleteById(Long id) {
        var developers = getDevelopersFromFile();
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

    private List<Developer> getDevelopersFromFile() {
        var result = new ArrayList<Developer>();

        try (BufferedReader in = Files.newBufferedReader(path)) {
            var lines = in.lines().collect(Collectors.toList());
            for (String line : lines) {
                var data = gson.fromJson(line, Developer.class);
                if (data != null) result.add(data);
            }
        } catch (NoSuchFileException e) {
            System.out.println("Missing file " + path.getFileName() + ": " + e);
        } catch (IOException e) {
            System.out.println("I/O Error");
            e.printStackTrace();
        }

        return result;
    }

    private void saveDevelopersToFile(List<Developer> developerList) {
        try (BufferedWriter out = new BufferedWriter(new FileWriter(filePath))) {
            String json;
            for (Developer data : developerList) {
                json = gson.toJson(data) + '\n';
                out.write(json);
            }
        } catch (IOException e) {
            System.out.println("I/O Error: " + e);
        }
    }
}
