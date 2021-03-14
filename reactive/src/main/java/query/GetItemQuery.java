package query;

import dao.ReactiveDao;
import rx.Observable;

public class GetItemQuery implements Query {
    private final long id;

    public GetItemQuery(long id) {
        this.id = id;
    }

    @Override
    public Observable<String> process(ReactiveDao dao) {
        return dao.getItemById(id).map(Object::toString);
    }
}
