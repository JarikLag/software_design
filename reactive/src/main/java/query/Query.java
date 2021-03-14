package query;

import dao.ReactiveDao;
import rx.Observable;

public interface Query {
    Observable<String> process(ReactiveDao dao);
}
