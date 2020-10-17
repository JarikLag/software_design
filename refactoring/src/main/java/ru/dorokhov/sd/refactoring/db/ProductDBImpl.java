package ru.dorokhov.sd.refactoring.db;

import ru.dorokhov.sd.refactoring.functional.FunctionalUtils;
import ru.dorokhov.sd.refactoring.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ProductDBImpl implements ProductDB {
    final String connectionPath;

    public ProductDBImpl(final String connectionPath) {
        this.connectionPath = connectionPath;
    }

    @Override
    public void createTable() throws SQLException {
        final String createDB =
                "CREATE TABLE IF NOT EXISTS PRODUCT " +
                        "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                        " NAME           TEXT    NOT NULL, " +
                        " PRICE          INT     NOT NULL)";
        executeUpdate(createDB);
    }

    @Override
    public void addProduct(Product product) throws SQLException {
        final String insert = String.format("INSERT INTO PRODUCT (NAME, PRICE) VALUES (\"%s\", %d)",
                product.getName(), product.getPrice());
        executeUpdate(insert);
    }

    @Override
    public List<Product> getAllProducts() throws SQLException {
        final String selectAll = "SELECT * FROM PRODUCT";
        final List<Product> products = new ArrayList<>();
        try (Connection c = DriverManager.getConnection(connectionPath)) {
            try (Statement stmt = c.createStatement()) {
                final ResultSet resultSet = stmt.executeQuery(selectAll);
                while (resultSet.next()) {
                    final String name = resultSet.getString("name");
                    final int price = resultSet.getInt("price");
                    products.add(new Product(name, price));
                }
            }
        }
        return products;
    }

    @Override
    public Product getMinPriceProduct() throws SQLException {
        final String minPriceQuery = "SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1";
        return getProductQuery(minPriceQuery);
    }

    @Override
    public Product getMaxPriceProduct() throws SQLException {
        final String minPriceQuery = "SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1";
        return getProductQuery(minPriceQuery);
    }

    @Override
    public int getProductsCount() throws SQLException {
        final String countProducts = "SELECT COUNT(*) FROM PRODUCT";
        return getScalarQuery(countProducts,
                FunctionalUtils.throwingFunctionWrapper(resultSet -> resultSet.getInt(1)));
    }

    @Override
    public long getSumPrices() throws SQLException {
        final String sumPrice = "SELECT SUM(price) FROM PRODUCT";
        return getScalarQuery(sumPrice,
                FunctionalUtils.throwingFunctionWrapper(resultSet -> resultSet.getLong(1)));
    }

    private void executeUpdate(final String query) throws SQLException {
        try (Connection c = DriverManager.getConnection(connectionPath)) {
            try (Statement stmt = c.createStatement()) {
                stmt.executeUpdate(query);
            }
        }
    }

    private Product getProductQuery(final String query) throws SQLException {
        try (Connection c = DriverManager.getConnection(connectionPath)) {
            try (Statement stmt = c.createStatement()) {
                final ResultSet resultSet = stmt.executeQuery(query);
                if (resultSet.next()) {
                    final String name = resultSet.getString("name");
                    final int price = resultSet.getInt("price");
                    return new Product(name, price);
                } else {
                    return null;
                }
            }
        }
    }

    private <T> T getScalarQuery(final String query, final Function<ResultSet, T> resultFetcher) throws SQLException {
        try (Connection c = DriverManager.getConnection(connectionPath)) {
            try (Statement stmt = c.createStatement()) {
                final ResultSet resultSet = stmt.executeQuery(query);
                if (resultSet.next()) {
                    return resultFetcher.apply(resultSet);
                } else {
                    throw new RuntimeException("Scalar query failed");
                }
            }
        }
    }
}
