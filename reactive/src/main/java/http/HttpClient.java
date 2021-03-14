package http;

import java.io.Closeable;
import java.io.IOException;

public interface HttpClient extends Closeable {
    String performGet(String url) throws IOException;
}
