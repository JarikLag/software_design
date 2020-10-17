package ru.dorokhov.sd.refactoring.db;

import ru.dorokhov.sd.refactoring.model.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDB {
    void createTable() throws SQLException;

    void addProduct(Product product) throws SQLException;

    List<Product> getAllProducts() throws SQLException;

    Product getMinPriceProduct() throws SQLException;

    Product getMaxPriceProduct() throws SQLException;

    int getProductsCount() throws SQLException;

    long getSumPrices() throws SQLException;
}
