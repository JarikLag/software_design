package ru.dorokhov.sd.refactoring;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.dorokhov.sd.refactoring.config.ApplicationConfig;
import ru.dorokhov.sd.refactoring.db.ProductDB;
import ru.dorokhov.sd.refactoring.db.ProductDBImpl;
import ru.dorokhov.sd.refactoring.servlet.AddProductServlet;
import ru.dorokhov.sd.refactoring.servlet.GetProductsServlet;
import ru.dorokhov.sd.refactoring.servlet.QueryServlet;

import java.io.FileInputStream;
import java.io.IOException;
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
        final ProductDB productDB = new ProductDBImpl(config.getDb());

        productDB.createTable();

        final Server server = new Server(config.getPort());

        final ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.setContextPath("/");
        server.setHandler(context);

        context.addServlet(new ServletHolder(new AddProductServlet(productDB)), "/add-product");
        context.addServlet(new ServletHolder(new GetProductsServlet(productDB)), "/get-products");
        context.addServlet(new ServletHolder(new QueryServlet(productDB)), "/query");

        server.start();
        server.join();
    }
}
