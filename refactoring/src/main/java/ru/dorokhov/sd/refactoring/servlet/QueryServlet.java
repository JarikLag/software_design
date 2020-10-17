package ru.dorokhov.sd.refactoring.servlet;

import ru.dorokhov.sd.refactoring.db.ProductDB;
import ru.dorokhov.sd.refactoring.model.Product;

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

        if ("max".equals(command)) {
            final Product product;
            try {
                product = productDB.getMaxPriceProduct();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            response.getWriter().println("<html><body>");
            response.getWriter().println("<h1>" + "Item with max price" + ": </h1>");
            response.getWriter().println(product.getName() + "\t" + product.getPrice() + "</br>");
            response.getWriter().println("</body></html>");
        } else if ("min".equals(command)) {
            final Product product;
            try {
                product = productDB.getMinPriceProduct();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            response.getWriter().println("<html><body>");
            response.getWriter().println("<h1>" + "Item with min price" + ": </h1>");
            response.getWriter().println(product.getName() + "\t" + product.getPrice() + "</br>");
            response.getWriter().println("</body></html>");
        } else if ("sum".equals(command)) {
            final long sum;
            try {
                sum = productDB.getSumPrices();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            response.getWriter().println("<html><body>");
            response.getWriter().println("Summary price: ");
            response.getWriter().println(sum);
            response.getWriter().println("</body></html>");
        } else if ("count".equals(command)) {
            final int count;
            try {
                count = productDB.getProductsCount();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            response.getWriter().println("<html><body>");
            response.getWriter().println("Number of products: ");
            response.getWriter().println(count);
            response.getWriter().println("</body></html>");
        } else {
            response.getWriter().println("Unknown command: " + command);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
