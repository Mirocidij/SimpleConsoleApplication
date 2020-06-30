package main.java.com.mirocidij.simpleconsoleapplication.generic.repository;

import main.java.com.mirocidij.simpleconsoleapplication.generic.entity.Entity;

public abstract class GenericRepository<
    T extends Entity<ID>,
    ID extends Number>
    implements GenericRepositoryInterface<T, ID> {}
