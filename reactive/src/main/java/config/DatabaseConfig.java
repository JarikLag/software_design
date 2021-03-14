package config;

import com.typesafe.config.Config;

public class DatabaseConfig {
    public final String schema;
    public final String host;
    public final int port;
    public final String name;

    public DatabaseConfig(String schema, String host, int port, String name) {
        this.schema = schema;
        this.host = host;
        this.port = port;
        this.name = name;
    }

    public DatabaseConfig(Config config) {
        this(
                config.getString("schema"),
                config.getString("host"),
                config.getInt("port"),
                config.getString("name")
        );
    }
}

