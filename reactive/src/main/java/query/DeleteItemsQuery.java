package query;

import dao.ReactiveDao;
import rx.Observable;

public class DeleteItemsQuery implements Query {
    @Override
    public Observable<String> process(ReactiveDao dao) {
        return dao.deleteAllItems().map(Object::toString);
    }
}
