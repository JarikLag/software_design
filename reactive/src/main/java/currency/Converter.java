package currency;

import java.util.Map;

public class Converter {
    private final Map<CurrencyConversionPair, Double> conversionRates;

    public Converter(Map<CurrencyConversionPair, Double> conversionRates) {
        this.conversionRates = conversionRates;
    }

    public double convert(CurrencyConversionPair pair, double amount) {
        return amount * conversionRates.get(pair);
    }
}
