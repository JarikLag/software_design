package ru.dorokhov.sd.refactoring.servlet;

import ru.dorokhov.sd.refactoring.db.ProductDB;
import ru.dorokhov.sd.refactoring.query.*;
import ru.dorokhov.sd.refactoring.response.ResponseBuilder;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

public class QueryServlet extends ProductServlet {
    private final ProductDB productDB;

    public QueryServlet(final ProductDB productDB) {
        this.productDB = productDB;
    }

    @Override
    protected String processRequest(final HttpServletRequest request) {
        final String command = request.getParameter("command");
        final SelectQuery query;
        final ResponseBuilder responseBuilder = new ResponseBuilder();

        switch (command) {
            case "max":
                query = new SelectMaxQuery();
                break;
            case "min":
                query = new SelectMinQuery();
                break;
            case "sum":
                query = new SelectSumQuery();
                break;
            case "count":
                query = new SelectCountQuery();
                break;
            default:
                return "Unknown command: " + command;
        }

        try {
            query.executeQuery(productDB, responseBuilder);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return responseBuilder.build();
    }

}
