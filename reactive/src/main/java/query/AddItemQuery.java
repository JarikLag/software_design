package query;

import dao.ReactiveDao;
import model.Item;
import rx.Observable;

public class AddItemQuery implements Query {
    private final Item item;

    public AddItemQuery(Item item) {
        this.item = item;
    }

    @Override
    public Observable<String> process(ReactiveDao dao) {
        return dao.addItem(item).map(Object::toString);
    }
}
