package http;

import org.apache.http.impl.client.HttpClients;

public class HttpClientProviderImpl implements HttpClientProvider {
    @Override
    public HttpClient getInstance() {
        return new HttpClientImpl(HttpClients.createDefault());
    }
}
