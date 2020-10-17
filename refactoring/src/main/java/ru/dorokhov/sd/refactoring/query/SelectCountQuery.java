package ru.dorokhov.sd.refactoring.query;

import ru.dorokhov.sd.refactoring.db.ProductDB;
import ru.dorokhov.sd.refactoring.response.ResponseBuilder;

import java.sql.SQLException;

public class SelectCountQuery extends SelectPrimitiveQuery {
    @Override
    public void executeQuery(final ProductDB productDB, final ResponseBuilder responseBuilder) throws SQLException {
        final Integer result = productDB.getProductsCount();
        process(result, responseBuilder);
    }

    @Override
    public String getQueryTitle() {
        return "Number of products: ";
    }
}
