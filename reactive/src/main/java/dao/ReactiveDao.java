package dao;

import com.mongodb.rx.client.Success;
import model.Item;
import model.User;
import rx.Observable;

public interface ReactiveDao {
    Observable<User> getUserById(long id);
    Observable<Item> getItemById(long id);
    Observable<Item> getItemsForUser(long id);

    Observable<Boolean> addUser(User user);
    Observable<Boolean> addItem(Item item);
    Observable<Success> deleteAllUsers();
    Observable<Success> deleteAllItems();
}
