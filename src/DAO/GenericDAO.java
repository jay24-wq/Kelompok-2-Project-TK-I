package DAO;

import java.sql.SQLException;
import java.util.List;

public interface GenericDAO<T> {
    T findById(int id) throws SQLException;
    List<T> findAll() throws SQLException;
    boolean save(T entity) throws SQLException; // Untuk INSERT/UPDATE
    boolean delete(int id) throws SQLException;
}
