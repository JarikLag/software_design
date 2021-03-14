package dao;

import com.mongodb.client.model.Filters;
import com.mongodb.rx.client.MongoDatabase;
import com.mongodb.rx.client.Success;
import currency.Converter;
import currency.ConverterProvider;
import currency.CurrencyConversionPair;
import model.Currency;
import model.Item;
import model.User;
import org.bson.Document;
import rx.Observable;
import rx.Scheduler;
import rx.schedulers.Schedulers;

import java.util.Map;

public class ReactiveDaoImpl implements ReactiveDao {
    private final MongoDatabase database;
    private final ConverterProvider converterProvider;
    private final Scheduler scheduler;

    public ReactiveDaoImpl(MongoDatabase database, ConverterProvider converterProvider) {
        this.database = database;
        this.converterProvider = converterProvider;
        this.scheduler = Schedulers.io();
    }

    @Override
    public Observable<User> getUserById(long id) {
        return database.getCollection("users")
                .find(Filters.eq("id", id))
                .toObservable()
                .map(x -> new User(id, Currency.fromString(x.getString("currency"))))
                .subscribeOn(scheduler);
    }

    @Override
    public Observable<Item> getItemById(long id) {
        return database.getCollection("items")
                .find(Filters.eq("id", id))
                .toObservable()
                .map(x -> new Item(
                        id,
                        x.getString("name"),
                        x.getDouble("price"),
                        Currency.fromString(x.getString("currency"))))
                .subscribeOn(scheduler);
    }

    @Override
    public Observable<Item> getItemsForUser(long id) {
        Converter converter = converterProvider.getConverter();
        return getUserById(id).flatMap(user ->
                database.getCollection("items")
                        .find()
                        .toObservable()
                        .map(item -> {
                            double originalPrice = item.getDouble("price");
                            Currency originalCurrency = Currency.fromString(item.getString("currency"));
                            CurrencyConversionPair pair = new CurrencyConversionPair(originalCurrency, user.selectedCurrency);
                            double convertedPrice = converter.convert(pair, originalPrice);
                            return new Item(
                                    item.getLong("id"),
                                    item.getString("name"),
                                    convertedPrice,
                                    user.selectedCurrency);
                        }))
                .subscribeOn(scheduler);
    }

    @Override
    public Observable<Boolean> addUser(User user) {
        return getUserById(user.id)
                .singleOrDefault(null)
                .flatMap(possibleUser -> {
                    if (possibleUser != null) {
                        return Observable.just(false);
                    } else {
                        Document doc = new Document(
                                Map.of("id", user.id,
                                       "currency", user.selectedCurrency.toString()
                                )
                        );
                        return database.getCollection("users")
                                .insertOne(doc)
                                .asObservable()
                                .isEmpty()
                                .map(x -> !x);
                    }
                })
                .subscribeOn(scheduler);
    }

    @Override
    public Observable<Boolean> addItem(Item item) {
        return getItemById(item.id)
                .singleOrDefault(null)
                .flatMap(possibleItem -> {
                    if (possibleItem != null) {
                        return Observable.just(false);
                    } else {
                        Document doc = new Document(
                                Map.of("id", item.id,
                                       "name", item.name,
                                       "price", item.price,
                                       "currency", item.currency.toString()
                                )
                        );
                        return database.getCollection("items")
                                .insertOne(doc)
                                .asObservable()
                                .isEmpty()
                                .map(x -> !x);
                    }
                })
                .subscribeOn(scheduler);
    }

    @Override
    public Observable<Success> deleteAllUsers() {
        return database.getCollection("users").drop().subscribeOn(scheduler);
    }

    @Override
    public Observable<Success> deleteAllItems() {
        return database.getCollection("items").drop().subscribeOn(scheduler);
    }
}
