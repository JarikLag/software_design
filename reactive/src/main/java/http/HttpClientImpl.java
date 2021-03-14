package http;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpClientImpl implements HttpClient {
    private final CloseableHttpClient client;

    public HttpClientImpl(CloseableHttpClient client) {
        this.client = client;
    }

    @Override
    public String performGet(String url) throws IOException {
        return client.execute(new HttpGet(url), response -> EntityUtils.toString(response.getEntity()));
    }

    @Override
    public void close() throws IOException {
        client.close();
    }
}
