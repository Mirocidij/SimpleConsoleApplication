package com.mirocidij.simpleconsoleapplication.generic.entity;

import lombok.Getter;
import lombok.Setter;

public abstract class Entity<T extends Number> {
    @Getter
    @Setter
    private T id;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Entity<?> entity) {
            if (id != null && entity.id != null)
                return id.equals(entity.id);
        }

        return super.equals(obj);
    }
}
