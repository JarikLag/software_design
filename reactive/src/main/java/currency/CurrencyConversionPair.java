package currency;

import model.Currency;

import java.util.Objects;

public class CurrencyConversionPair {
    public final Currency from;
    public final Currency to;

    public CurrencyConversionPair(Currency from, Currency to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrencyConversionPair that = (CurrencyConversionPair) o;
        return from == that.from && to == that.to;
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }
}
