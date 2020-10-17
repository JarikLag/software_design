package ru.dorokhov.sd.refactoring.query;

import ru.dorokhov.sd.refactoring.db.ProductDB;
import ru.dorokhov.sd.refactoring.model.Product;
import ru.dorokhov.sd.refactoring.response.ResponseBuilder;

import java.sql.SQLException;

public class SelectMinQuery extends SelectProductQuery {
    @Override
    public void executeQuery(final ProductDB productDB, final ResponseBuilder responseBuilder) throws SQLException {
        final Product product = productDB.getMinPriceProduct();
        process(product, responseBuilder);
    }

    @Override
    public String getQueryTitle() {
        return "Item with min price: ";
    }
}
