package com.mirocidij.simpleconsoleapplication.repositories;

import com.google.gson.Gson;
import com.mirocidij.simpleconsoleapplication.generic.entity.Entity;
import com.mirocidij.simpleconsoleapplication.generic.repository.GenericRepository;
import com.mirocidij.simpleconsoleapplication.utils.PathBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractRepository
    <T extends Entity<TID>, TID extends Number>
    implements GenericRepository<T, TID> {
    protected final Path path;
    protected final String filePath;
    private final Class<T> type;
    protected final Gson gson;

    protected AbstractRepository(Gson gson, String fileName, Class<T> type) {
        this.gson = gson;
        this.filePath = PathBuilder.buildPath(fileName);
        this.type = type;
        this.path = Paths.get(filePath);
    }

    protected List<T> getDataFromFile() {
        var result = new ArrayList<T>();

        try (BufferedReader in = Files.newBufferedReader(path)) {
            var lines = in.lines().collect(Collectors.toList());
            for (String line : lines) {
                var data = gson.fromJson(line, type);
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
}
