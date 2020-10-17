package ru.dorokhov.sd.refactoring.query;

import ru.dorokhov.sd.refactoring.db.ProductDB;
import ru.dorokhov.sd.refactoring.model.Product;
import ru.dorokhov.sd.refactoring.response.ResponseBuilder;

import java.sql.SQLException;

public abstract class SelectProductQuery implements SelectQuery {
    @Override
    public abstract void executeQuery(ProductDB productDB, ResponseBuilder responseBuilder) throws SQLException;

    @Override
    public abstract String getQueryTitle();

    protected void process(final Product product, ResponseBuilder responseBuilder) {
        responseBuilder.addH1Title(getQueryTitle());
        if (product != null) {
            responseBuilder.addElement(product.getName() + "\t" + product.getPrice());
        }
    }
}
