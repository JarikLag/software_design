package currency;

import com.google.gson.Gson;
import config.ConverterConfig;
import http.HttpClient;
import model.Currency;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class ConverterProvider {
    private final AtomicReference<Converter> converterRef = new AtomicReference<>(getDefaultConverter());
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private final ConverterConfig config;
    private final HttpClient client;

    public ConverterProvider(ConverterConfig config, HttpClient client) {
        this.config = config;
        this.client = client;

        try {
            Converter converter = getUpdatedConverter();
            converterRef.set(converter);
        } catch (IOException e) {
            System.err.println("Cannot obtain currency converter");
        }

        Runnable updateTask = () -> {
            try {
                Converter newConverter = getUpdatedConverter();
                Converter oldConverter = getConverter();
                converterRef.compareAndSet(oldConverter, newConverter);
            } catch (Exception ex) {
                System.err.println("Failed to update rates");
            }
        };
        scheduler.schedule(updateTask, 1, TimeUnit.HOURS);
    }

    public Converter getConverter() {
        return converterRef.get();
    }

    private Converter getUpdatedConverter() throws IOException {
        String symbols = Arrays.stream(Currency.values())
                .map(Currency::toString)
                .collect(Collectors.joining(","));
        String url = config.schema + "://" + config.host + "/" + config.path + "?base=USD&symbols=" + symbols;
        String response = "";
        try {
            response = client.performGet(url);
        } finally {
            client.close();
        }
        Gson gson = new Gson();
        CurrencyRatesResponse ratesResponse = gson.fromJson(response, CurrencyRatesResponse.class);
        return new Converter(getConversionMap(ratesResponse));
    }

    private Map<CurrencyConversionPair, Double> getConversionMap(CurrencyRatesResponse ratesResponse) {
        Map<CurrencyConversionPair, Double> map = new HashMap<>();
        for (Currency from : Currency.values()) {
            for (Currency to: Currency.values()) {
                CurrencyConversionPair pair = new CurrencyConversionPair(from, to);
                double val = 1.0;
                if (from != to) {
                    val = ratesResponse.rates.get(to) / ratesResponse.rates.get(from);
                }
                map.put(pair, val);
            }
         }
        return map;
    }

    private Converter getDefaultConverter() {
        Map<Currency, Double> map = new HashMap<>();
        for (Currency currency : Currency.values()) {
            map.put(currency, 1.0);
        }
        return new Converter(getConversionMap(new CurrencyRatesResponse(map, "USD", "")));
    }

}
