package ru.dorokhov.sd.refactoring.db;

import org.junit.Before;
import org.junit.Test;
import ru.dorokhov.sd.refactoring.config.ApplicationConfig;
import ru.dorokhov.sd.refactoring.model.Product;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ProductDBImplTest {
    private final ApplicationConfig config = getConfig();
    private ProductDB productDB;

    private ApplicationConfig getConfig() {
        final Properties properties = new Properties();
        try (final FileInputStream propsFile = new FileInputStream("src/test/resources/application-test.properties")) {
            properties.load(propsFile);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        return new ApplicationConfig(properties);
    }

    private List<Product> getTestProducts() {
        return asList(
                new Product("p1", 100),
                new Product("p2", 200),
                new Product("p3", 300),
                new Product("p4", 500)
        );
    }

    @Before
    public void setUp() throws SQLException  {
        final String dropTable = "DROP TABLE IF EXISTS PRODUCT";
        final String add = "INSERT INTO PRODUCT (NAME, PRICE) VALUES (\"%s\", %d)";
        final String createDB =
                "CREATE TABLE IF NOT EXISTS PRODUCT " +
                        "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                        " NAME           TEXT    NOT NULL, " +
                        " PRICE          INT     NOT NULL)";
        try (Connection c = DriverManager.getConnection(config.getDb())) {
            try (Statement stmt = c.createStatement()) {
                stmt.executeUpdate(dropTable);
                stmt.executeUpdate(createDB);
                for (final Product product : getTestProducts()) {
                    stmt.executeUpdate(String.format(add, product.getName(), product.getPrice()));
                }
            }
        }

        productDB = new ProductDBImpl(config.getDb());
    }

    @Test
    public void testGetAllProducts() throws SQLException {
        final List<Product> products = productDB.getAllProducts();
        assertEquals(getTestProducts().size(), products.size());
        assertTrue(getTestProducts().containsAll(products));
    }

    @Test
    public void testAddProduct() throws SQLException {
        final Product product = new Product("test", 123);
        productDB.addProduct(product);
        final List<Product> products = productDB.getAllProducts();
        assertTrue(products.contains(product));
    }

    @Test
    public void testCount() throws SQLException {
        assertEquals(getTestProducts().size(), productDB.getProductsCount());
    }

    @Test
    public void testSum() throws SQLException {
        long expectedSum = 0;
        for (final Product p : getTestProducts()) {
            expectedSum += p.getPrice();
        }
        assertEquals(expectedSum, productDB.getSumPrices());
    }

    @Test
    public void testMin() throws SQLException {
        Integer minPrice = null;
        Product expectedProduct = null;
        for (final Product p : getTestProducts()) {
            if (minPrice == null || minPrice > p.getPrice()) {
                minPrice = p.getPrice();
                expectedProduct = p;
            }
        }
        assertEquals(expectedProduct, productDB.getMinPriceProduct());
    }

    @Test
    public void testMax() throws SQLException {
        Integer maxPrice = null;
        Product expectedProduct = null;
        for (final Product p : getTestProducts()) {
            if (maxPrice == null || maxPrice < p.getPrice()) {
                maxPrice = p.getPrice();
                expectedProduct = p;
            }
        }
        assertEquals(expectedProduct, productDB.getMaxPriceProduct());
    }
}
