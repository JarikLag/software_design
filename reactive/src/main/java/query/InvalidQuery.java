package query;

import dao.ReactiveDao;
import rx.Observable;

public class InvalidQuery implements Query {
    private final String message;

    public InvalidQuery(String message) {
        this.message = message;
    }

    @Override
    public Observable<String> process(ReactiveDao dao) {
        return Observable.just("Invalid query. " + message);
    }
}
