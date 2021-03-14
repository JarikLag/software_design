package query;

import dao.ReactiveDao;
import rx.Observable;

public class GetUserQuery implements Query {
    private final long id;

    public GetUserQuery(long id) {
        this.id = id;
    }

    @Override
    public Observable<String> process(ReactiveDao dao) {
        return dao.getUserById(id).map(Object::toString);
    }
}
