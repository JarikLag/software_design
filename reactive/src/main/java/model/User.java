package model;

import java.util.Objects;

public class User {
    public final long id;
    public final Currency selectedCurrency;

    public User(long id, Currency selectedCurrency) {
        this.id = id;
        this.selectedCurrency = selectedCurrency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && selectedCurrency == user.selectedCurrency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, selectedCurrency);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", selectedCurrency=" + selectedCurrency +
                '}';
    }
}
