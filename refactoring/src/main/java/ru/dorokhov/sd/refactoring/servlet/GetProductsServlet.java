package ru.dorokhov.sd.refactoring.servlet;

import ru.dorokhov.sd.refactoring.db.ProductDB;
import ru.dorokhov.sd.refactoring.model.Product;
import ru.dorokhov.sd.refactoring.response.ResponseBuilder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class GetProductsServlet extends HttpServlet {
    private final ProductDB productDB;

    public GetProductsServlet(final ProductDB productDB) {
        this.productDB = productDB;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
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

        response.getWriter().println(responseBuilder.build());
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
