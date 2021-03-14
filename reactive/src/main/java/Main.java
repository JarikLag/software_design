import com.mongodb.rx.client.MongoClients;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import config.ConverterConfig;
import config.DatabaseConfig;
import config.ServerConfig;
import currency.ConverterProvider;
import dao.ReactiveDao;
import dao.ReactiveDaoImpl;
import http.HttpClientProvider;
import http.HttpClientProviderImpl;
import io.reactivex.netty.protocol.http.server.HttpServer;
import query.Query;
import query.QueryUtils;

import java.io.File;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        File configFile = Paths.get("src/main/resources/app.conf").toFile();
        Config appConfig = ConfigFactory.parseFile(configFile);
        ConverterConfig converterConfig = new ConverterConfig(appConfig.getConfig("converter"));
        DatabaseConfig databaseConfig = new DatabaseConfig(appConfig.getConfig("database"));
        ServerConfig serverConfig = new ServerConfig(appConfig.getConfig("server"));
        HttpClientProvider httpClientProvider = new HttpClientProviderImpl();
        ConverterProvider converterProvider = new ConverterProvider(converterConfig, httpClientProvider.getInstance());
        String mongoUrl = databaseConfig.schema + "://" + databaseConfig.host + ":" + databaseConfig.port;
        ReactiveDao dao = new ReactiveDaoImpl(
                MongoClients.create(mongoUrl).getDatabase(databaseConfig.name),
                converterProvider
        );
        HttpServer.newServer(serverConfig.port)
                .start((request, response) -> {
                    Query query = QueryUtils.fromRequest(request);
                    return response.writeString(query.process(dao).map(x -> x + "\n"));
                })
                .awaitShutdown();
    }
}
