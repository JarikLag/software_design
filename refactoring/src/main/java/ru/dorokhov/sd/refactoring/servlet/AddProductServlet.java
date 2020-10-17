package ru.dorokhov.sd.refactoring.servlet;

import ru.dorokhov.sd.refactoring.db.ProductDB;
import ru.dorokhov.sd.refactoring.model.Product;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

public class AddProductServlet extends ProductServlet {
    private final ProductDB productDB;

    public AddProductServlet(final ProductDB productDB) {
        this.productDB = productDB;
    }

    @Override
    protected String processRequest(final HttpServletRequest request) {
        final String name = request.getParameter("name");
        final int price = Integer.parseInt(request.getParameter("price"));

        try {
            productDB.addProduct(new Product(name, price));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return "OK";
    }
}
