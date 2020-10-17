package ru.dorokhov.sd.refactoring.servlet;

import ru.dorokhov.sd.refactoring.db.ProductDB;
import ru.dorokhov.sd.refactoring.model.Product;
import ru.dorokhov.sd.refactoring.response.ResponseBuilder;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

public class GetProductsServlet extends ProductServlet {
    private final ProductDB productDB;

    public GetProductsServlet(final ProductDB productDB) {
        this.productDB = productDB;
    }

    @Override
    protected String processRequest(final HttpServletRequest request) {
        final List<Product> products;
        try {
            products = productDB.getAllProducts();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        final ResponseBuilder responseBuilder = new ResponseBuilder();
        responseBuilder.addH1Title("All items that we have: ");

        for (final Product product : products) {
            responseBuilder.addElement(product.getName() + "\t" + product.getPrice());
        }

        return responseBuilder.build();
    }
}
