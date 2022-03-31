package com.irdaislakhuafa.springbootjwtintegrations.services;

import java.util.List;
import java.util.Optional;

public interface BaseService<A, B> {
    public Optional<A> save(A entity);

    public Optional<A> findById(String id);

    public List<A> findAll();

    public void deleteById(String id);

    public Optional<A> update(A entity);

    public A mapDtoToEntity(B dto);

    public List<A> mapDtosToEntities(List<B> dtos);
}
