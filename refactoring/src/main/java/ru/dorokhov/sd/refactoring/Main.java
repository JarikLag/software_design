package ru.dorokhov.sd.refactoring;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.dorokhov.sd.refactoring.config.ApplicationConfig;
import ru.dorokhov.sd.refactoring.servlet.AddProductServlet;
import ru.dorokhov.sd.refactoring.servlet.GetProductsServlet;
import ru.dorokhov.sd.refactoring.servlet.QueryServlet;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws Exception {
        final Properties properties = new Properties();
        try (final FileInputStream propsFile = new FileInputStream("src/main/resources/application.properties")) {
            properties.load(propsFile);
        } catch (IOException e) {
            System.err.println("Cannot load config from file application.properties.");
            return;
        }

        final ApplicationConfig config = new ApplicationConfig(properties);

        try (Connection c = DriverManager.getConnection(config.getDb())) {
            String sql = "CREATE TABLE IF NOT EXISTS PRODUCT" +
                    "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    " NAME           TEXT    NOT NULL, " +
                    " PRICE          INT     NOT NULL)";
            Statement stmt = c.createStatement();

            stmt.executeUpdate(sql);
            stmt.close();
        }

        Server server = new Server(config.getPort());

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        context.addServlet(new ServletHolder(new AddProductServlet()), "/add-product");
        context.addServlet(new ServletHolder(new GetProductsServlet()), "/get-products");
        context.addServlet(new ServletHolder(new QueryServlet()), "/query");

        server.start();
        server.join();
    }
}
