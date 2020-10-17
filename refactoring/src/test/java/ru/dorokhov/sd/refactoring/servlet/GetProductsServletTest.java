package ru.dorokhov.sd.refactoring.servlet;

import org.junit.Test;
import ru.dorokhov.sd.refactoring.db.ProductDB;
import ru.dorokhov.sd.refactoring.model.Product;

import javax.servlet.http.HttpServletRequest;

import java.sql.SQLException;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GetProductsServletTest {
    @Test
    public void getProductsTest() throws SQLException {
        final Product product1 = new Product("p1", 100);
        final Product product2 = new Product("p2", 200);
        final List<Product> products = asList(product1, product2);
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final ProductDB dao = mock(ProductDB.class);
        when(dao.getAllProducts()).thenReturn(products);
        final GetProductsServlet servlet = new GetProductsServlet(dao);
        final String actualResult = servlet.processRequest(request);
        final String expectedResult = String.format(
                "<html><body>\n" +
                "<h1>All items that we have: </h1>\n" +
                "%s\t%d<br>\n" +
                "%s\t%d<br>\n" +
                "</body></html>\n",
                product1.getName(), product1.getPrice(),
                product2.getName(), product2.getPrice());

        assertEquals(expectedResult, actualResult);
    }
}
