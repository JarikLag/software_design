package ru.dorokhov.sd.refactoring.servlet;

import ru.dorokhov.sd.refactoring.db.ProductDB;
import ru.dorokhov.sd.refactoring.model.Product;
import ru.dorokhov.sd.refactoring.response.ResponseBuilder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class QueryServlet extends HttpServlet {
    private final ProductDB productDB;

    public QueryServlet(final ProductDB productDB) {
        this.productDB = productDB;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String command = request.getParameter("command");
        final ResponseBuilder responseBuilder = new ResponseBuilder();

        if ("max".equals(command)) {
            final Product product;
            try {
                product = productDB.getMaxPriceProduct();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            responseBuilder.addH1Title("Item with max price: ");
            responseBuilder.addElement(product.getName() + "\t" + product.getPrice());
            response.getWriter().println(responseBuilder.build());
        } else if ("min".equals(command)) {
            final Product product;
            try {
                product = productDB.getMinPriceProduct();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            responseBuilder.addH1Title("Item with min price: ");
            responseBuilder.addElement(product.getName() + "\t" + product.getPrice());
            response.getWriter().println(responseBuilder.build());
        } else if ("sum".equals(command)) {
            final long sum;
            try {
                sum = productDB.getSumPrices();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            responseBuilder.addTitle("Summary price: ");
            responseBuilder.addElement(Long.toString(sum));
            response.getWriter().println(responseBuilder.build());
        } else if ("count".equals(command)) {
            final int count;
            try {
                count = productDB.getProductsCount();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            responseBuilder.addTitle("Number of products: ");
            responseBuilder.addElement(Integer.toString(count));
            response.getWriter().println(responseBuilder.build());
        } else {
            response.getWriter().println("Unknown command: " + command);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
