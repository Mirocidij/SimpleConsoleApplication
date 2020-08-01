package com.mirocidij.simpleconsoleapplication.generic.repository;

import com.mirocidij.simpleconsoleapplication.generic.entity.Entity;

import java.util.List;

public interface GenericRepository<T extends Entity<TID>, TID extends Number> {
    List<T> getAll();

    T getById(TID id);

    T save(T t);

    T update(T t);

    boolean deleteById(TID id);
}
