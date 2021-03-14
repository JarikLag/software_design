package query;

import dao.ReactiveDao;
import model.User;
import rx.Observable;

public class AddUserQuery implements Query {
    private final User user;

    public AddUserQuery(User user) {
        this.user = user;
    }

    @Override
    public Observable<String> process(ReactiveDao dao) {
        return dao.addUser(user).map(Object::toString);
    }
}
