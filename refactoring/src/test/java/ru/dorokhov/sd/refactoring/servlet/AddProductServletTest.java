package ru.dorokhov.sd.refactoring.servlet;

import org.junit.Test;
import ru.dorokhov.sd.refactoring.db.ProductDB;
import ru.dorokhov.sd.refactoring.model.Product;

import javax.servlet.http.HttpServletRequest;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class AddProductServletTest {

    @Test
    public void addProductTest() throws SQLException {
        final Product product = new Product("prod", 123);
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final ProductDB dao = mock(ProductDB.class);
        when(request.getParameter("name")).thenReturn(product.getName());
        when(request.getParameter("price")).thenReturn(Integer.toString(product.getPrice()));
        doNothing().when(dao).addProduct(product);
        final AddProductServlet servlet = new AddProductServlet(dao);
        final String result = servlet.processRequest(request);
        verify(dao, times(1)).addProduct(product);
        assertEquals("OK", result);
    }
}
