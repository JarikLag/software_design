package query;

import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import model.Currency;
import model.Item;
import model.User;

import java.util.List;

public final class QueryUtils {
    public static <T> Query fromRequest(HttpServerRequest<T> request) {
        QueryType queryType = QueryType.fromString(request.getDecodedPath().substring(1));
        switch (queryType) {
            case ADD_ITEM: {
                long id = Long.parseLong(getParam(request, "id"));
                String name = getParam(request, "name");
                double price = Double.parseDouble(getParam(request, "price"));
                Currency currency = Currency.fromString(getParam(request, "currency"));
                return new AddItemQuery(new Item(id, name, price, currency));
            }
            case ADD_USER: {
                long id = Long.parseLong(getParam(request, "id"));
                Currency currency = Currency.fromString(getParam(request, "currency"));
                return new AddUserQuery(new User(id, currency));
            }
            case DELETE_ITEMS:
                return new DeleteItemsQuery();
            case DELETE_USERS:
                return new DeleteUsersQuery();
            case GET_ITEM: {
                long id = Long.parseLong(getParam(request, "id"));
                return new GetItemQuery(id);
            }
            case GET_ITEMS_FOR_USER: {
                long id = Long.parseLong(getParam(request, "id"));
                return new GetItemsForUserQuery(id);
            }
            case GET_USER: {
                long id = Long.parseLong(getParam(request, "id"));
                return new GetUserQuery(id);
            }
            default:
                return new InvalidQuery("No such query: " + queryType);
        }
    }

    private static <T> String getParam(HttpServerRequest<T> request, String name) {
        List<String> paramsList = request.getQueryParameters().get(name);
        if (paramsList == null) {
            throw new IllegalArgumentException("Parameter " + name + "is required");
        }
        return paramsList.get(0);
    }
}
