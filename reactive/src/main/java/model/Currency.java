package model;

public enum Currency {
    RUB, USD, EUR;

    public static Currency fromString(String str) {
        for (Currency currency : Currency.values()) {
            if (currency.name().equalsIgnoreCase(str)) {
                return currency;
            }
        }
        throw new IllegalArgumentException("Unknown currency: " + str);
    }
}
