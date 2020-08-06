package com.mirocidij.simpleconsoleapplication.repositories;

import com.mirocidij.simpleconsoleapplication.generic.entity.Entity;
import com.mirocidij.simpleconsoleapplication.generic.repository.GenericRepository;

public abstract class AbstractRepository<T extends Entity<TID>, TID extends Number> implements GenericRepository<T, TID> {}
