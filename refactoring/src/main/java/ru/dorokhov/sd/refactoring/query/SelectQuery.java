package ru.dorokhov.sd.refactoring.query;

import ru.dorokhov.sd.refactoring.db.ProductDB;
import ru.dorokhov.sd.refactoring.response.ResponseBuilder;

import java.sql.SQLException;

public interface SelectQuery {
    void executeQuery(ProductDB productDB, ResponseBuilder responseBuilder) throws SQLException;

    String getQueryTitle();
}
