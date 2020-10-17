package ru.dorokhov.sd.refactoring.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class ProductServlet extends HttpServlet {
    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        final String result = processRequest(request);
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(result);
    }

    protected abstract String processRequest(final HttpServletRequest request);
}
