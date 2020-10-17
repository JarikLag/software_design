package ru.dorokhov.sd.refactoring.query;

import ru.dorokhov.sd.refactoring.db.ProductDB;
import ru.dorokhov.sd.refactoring.response.ResponseBuilder;

import java.sql.SQLException;

public class SelectSumQuery extends SelectPrimitiveQuery {
    @Override
    public void executeQuery(final ProductDB productDB, final ResponseBuilder responseBuilder) throws SQLException {
        final Long result = productDB.getSumPrices();
        process(result, responseBuilder);
    }

    @Override
    public String getQueryTitle() {
        return "Summary price: ";
    }
}
