package ru.dorokhov.sd.refactoring.servlet;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class ServletCommon {
    public static void dumpItems(HttpServletResponse response, ResultSet rs, String title)
            throws IOException, SQLException {
        response.getWriter().println("<html><body>");
        response.getWriter().println("<h1>" + title + ": </h1>");
        while (rs.next()) {
            String name = rs.getString("name");
            int price = rs.getInt("price");
            response.getWriter().println(name + "\t" + price + "</br>");
        }
        response.getWriter().println("</body></html>");
    }

    public static void doGoodies(HttpServletResponse response, DBHandler<Statement> handler) {
        try {
            try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                Statement stmt = c.createStatement();
                handler.apply(stmt);
                stmt.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}