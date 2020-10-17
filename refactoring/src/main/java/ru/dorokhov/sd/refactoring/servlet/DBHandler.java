package ru.dorokhov.sd.refactoring.servlet;

import java.io.IOException;
import java.sql.SQLException;

@FunctionalInterface
public interface DBHandler<T> {
    void apply(T t) throws SQLException, IOException;
}
