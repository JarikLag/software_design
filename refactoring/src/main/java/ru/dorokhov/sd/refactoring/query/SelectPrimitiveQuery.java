package ru.dorokhov.sd.refactoring.query;

import ru.dorokhov.sd.refactoring.db.ProductDB;
import ru.dorokhov.sd.refactoring.response.ResponseBuilder;

import java.sql.SQLException;

public abstract class SelectPrimitiveQuery implements SelectQuery {
    @Override
    public abstract void executeQuery(ProductDB productDB, ResponseBuilder responseBuilder) throws SQLException;

    @Override
    public abstract String getQueryTitle();

    protected <T> void process(final T result, ResponseBuilder responseBuilder) {
        responseBuilder.addTitle(getQueryTitle());
        responseBuilder.addElement(result.toString());
    }
}
