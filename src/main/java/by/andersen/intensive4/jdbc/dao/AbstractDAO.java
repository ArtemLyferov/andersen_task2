package by.andersen.intensive4.jdbc.dao;

import by.andersen.intensive4.entities.Entity;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.util.List;

@AllArgsConstructor
public abstract class AbstractDAO<T extends Entity> {

    protected Connection connection;

    public abstract int create(T entity);

    public abstract List<T> findAll();

    public abstract T findEntityById(int id);

    public abstract int update(T entity);

    public abstract int delete(int id);
}
