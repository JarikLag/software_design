package query;

import dao.ReactiveDao;
import rx.Observable;

public class DeleteUsersQuery implements Query {
    @Override
    public Observable<String> process(ReactiveDao dao) {
        return dao.deleteAllUsers().map(Object::toString);
    }
}
