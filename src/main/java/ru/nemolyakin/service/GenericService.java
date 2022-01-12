package ru.nemolyakin.service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public interface GenericService <T, ID> {

    T save(T t);

    List<T> findAll();

    T findById(ID id) throws EntityNotFoundException;

    void deleteById(ID id);

    boolean isExists(ID id);

    T update(T t);
}
