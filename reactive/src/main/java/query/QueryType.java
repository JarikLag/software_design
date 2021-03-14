package query;

public enum QueryType {
    ADD_ITEM, ADD_USER, DELETE_ITEMS, DELETE_USERS, GET_ITEM, GET_ITEMS_FOR_USER, GET_USER;

    public static QueryType fromString(String str) {
        for (QueryType queryType : QueryType.values()) {
            if (queryType.name().equalsIgnoreCase(str)) {
                return queryType;
            }
        }
        throw new IllegalArgumentException("Unknown query type: " + str);
    }
}
