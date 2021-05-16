package by.andersen.intensive4.jdbc.dao;

import by.andersen.intensive4.entities.Entity;
import by.andersen.intensive4.jdbc.connector.ConnectorDB;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public abstract class EntityDAO<T extends Entity> {
    @Getter
    private ConnectorDB connectorDB;

    public abstract int create(T entity);

    public abstract List<T> findAll();

    public abstract T findById(int id);

    public abstract int update(T entity);

    public abstract int delete(int id);
}
