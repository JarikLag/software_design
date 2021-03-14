package model;

import java.util.Objects;

public class Item {
    public final long id;
    public final String name;
    public final double price;
    public final Currency currency;

    public Item(long id, String name, double price, Currency currency) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id == item.id && Double.compare(item.price, price) == 0 && Objects.equals(name, item.name) && currency == item.currency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, currency);
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", currency=" + currency +
                '}';
    }
}
