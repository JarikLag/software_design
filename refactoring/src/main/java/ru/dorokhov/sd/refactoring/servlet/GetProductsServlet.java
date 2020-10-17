package ru.dorokhov.sd.refactoring.servlet;

import ru.dorokhov.sd.refactoring.db.ProductDB;
import ru.dorokhov.sd.refactoring.model.Product;

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

        response.getWriter().println("<html><body>");
        response.getWriter().println("<h1>" + "All items that we have" + ": </h1>");

        for (final Product product : products) {
            response.getWriter().println(product.getName() + "\t" + product.getPrice() + "</br>");
        }

        response.getWriter().println("</body></html>");
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
