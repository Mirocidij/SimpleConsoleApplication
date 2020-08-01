package com.mirocidij.simpleconsoleapplication.utils;

import com.mirocidij.simpleconsoleapplication.generic.entity.Entity;

import java.util.List;

public class EntityUtils {
    public static <T extends Entity<? extends Number>> T findById(
        Number id,
        List<T> entities
    ) {
        return entities
            .stream()
            .filter(x -> x.getId().equals(id))
            .findFirst()
            .orElse(null);
    }
}
