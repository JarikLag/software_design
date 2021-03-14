package query;

import dao.ReactiveDao;
import rx.Observable;

public class GetItemsForUserQuery implements Query {
    private final long id;

    public GetItemsForUserQuery(long id) {
        this.id = id;
    }

    @Override
    public Observable<String> process(ReactiveDao dao) {
        return dao.getItemsForUser(id).map(Object::toString);
    }
}
