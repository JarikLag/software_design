package ru.dorokhov.sd.refactoring.servlet;

import org.junit.Test;
import ru.dorokhov.sd.refactoring.db.ProductDB;
import ru.dorokhov.sd.refactoring.model.Product;

import javax.servlet.http.HttpServletRequest;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class QueryServletTest {
    @Test
    public void testGetMax() throws SQLException {
        final Product maxProduct = new Product("p5", 1000);
        final HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("command")).thenReturn("max");
        final ProductDB dao = mock(ProductDB.class);
        when(dao.getMaxPriceProduct()).thenReturn(maxProduct);
        final QueryServlet servlet = new QueryServlet(dao);
        final String result = servlet.processRequest(request);
        final String expectedResult = String.format(
                "<html><body>\n" +
                        "<h1>Item with max price: </h1>\n" +
                        "%s\t%d<br>\n" +
                        "</body></html>\n",
                maxProduct.getName(), maxProduct.getPrice());
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetMaxWithNull() throws SQLException {
        final HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("command")).thenReturn("max");
        final ProductDB dao = mock(ProductDB.class);
        when(dao.getMaxPriceProduct()).thenReturn(null);
        final QueryServlet servlet = new QueryServlet(dao);
        final String result = servlet.processRequest(request);
        final String expectedResult = "<html><body>\n" +
                "<h1>Item with max price: </h1>\n" +
                "</body></html>\n";
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetMin() throws SQLException {
        final Product minProduct = new Product("p1", 100);
        final HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("command")).thenReturn("min");
        final ProductDB dao = mock(ProductDB.class);
        when(dao.getMinPriceProduct()).thenReturn(minProduct);
        final QueryServlet servlet = new QueryServlet(dao);
        final String result = servlet.processRequest(request);
        final String expectedResult = String.format(
                "<html><body>\n" +
                "<h1>Item with min price: </h1>\n" +
                "%s\t%d<br>\n" +
                "</body></html>\n",
                minProduct.getName(), minProduct.getPrice());
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetMinWithNull() throws SQLException {
        final HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("command")).thenReturn("min");
        final ProductDB dao = mock(ProductDB.class);
        when(dao.getMinPriceProduct()).thenReturn(null);
        final QueryServlet servlet = new QueryServlet(dao);
        final String result = servlet.processRequest(request);
        final String expectedResult = "<html><body>\n" +
                "<h1>Item with min price: </h1>\n" +
                "</body></html>\n";
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetSum() throws SQLException {
        final long sum = 12345678901L;
        final HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("command")).thenReturn("sum");
        final ProductDB dao = mock(ProductDB.class);
        when(dao.getSumPrices()).thenReturn(sum);
        final QueryServlet servlet = new QueryServlet(dao);
        final String result = servlet.processRequest(request);
        final String expectedResult = String.format("<html><body>\n" +
                "Summary price: \n" +
                "%d<br>\n" +
                "</body></html>\n", sum);
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetCount() throws SQLException {
        final int count = 1337;
        final HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("command")).thenReturn("count");
        final ProductDB dao = mock(ProductDB.class);
        when(dao.getProductsCount()).thenReturn(count);
        final QueryServlet servlet = new QueryServlet(dao);
        final String result = servlet.processRequest(request);
        final String expectedResult = String.format("<html><body>\n" +
                "Number of products: \n" +
                "%d<br>\n" +
                "</body></html>\n", count);
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetUnknown() {
        final HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("command")).thenReturn("abracadabra");
        final ProductDB dao = mock(ProductDB.class);
        final QueryServlet servlet = new QueryServlet(dao);
        final String result = servlet.processRequest(request);
        final String expectedResult = "<html><body>\n" +
                "Unknown command: abracadabra\n" +
                "</body></html>\n";
        assertEquals(expectedResult, result);
    }
}
