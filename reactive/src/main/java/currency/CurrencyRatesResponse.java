package currency;

import model.Currency;

import java.util.Map;

public class CurrencyRatesResponse {
    public final Map<Currency, Double> rates;
    public final String base;
    public final String date;

    public CurrencyRatesResponse(Map<Currency, Double> rates, String base, String date) {
        this.rates = rates;
        this.base = base;
        this.date = date;
    }
}
