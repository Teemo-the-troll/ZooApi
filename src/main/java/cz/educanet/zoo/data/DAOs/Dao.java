package cz.educanet.zoo.data.DAOs;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {

    Optional<T> get(int id);

    List<T> getAll();

    Optional<T> save(T t);

    Optional<T> update(T t, int id);

    Optional<T> delete(int id);
}
