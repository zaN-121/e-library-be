package com.elibrary.group4.service;

import java.util.Optional;

public interface IService <T>{

    public Iterable <T> findAll();
    public T add(T params);
    public Optional<T> findById(String id);
    public void delete(String id);
    public T update(String id, T params);

}
