package com.mirocidij.simpleconsoleapplication.generic.entity;

import lombok.Getter;
import lombok.Setter;

public abstract class Entity<T extends Number> {
    @Getter
    @Setter
    protected T id;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Entity<?> entity) {
            return this.id.equals(entity.id);
        }

        return this.equals(obj);
    }
}
