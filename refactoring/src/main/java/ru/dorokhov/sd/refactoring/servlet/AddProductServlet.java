package ru.dorokhov.sd.refactoring.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Statement;

public class AddProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        long price = Long.parseLong(request.getParameter("price"));

        ServletCommon.doGoodies(
                response,
                (Statement stmt) -> {
                    String sql = "INSERT INTO PRODUCT " +
                            "(NAME, PRICE) VALUES (\"" + name + "\"," + price + ")";
                    stmt.executeUpdate(sql);
                });

        response.getWriter().println("OK");
    }
}
