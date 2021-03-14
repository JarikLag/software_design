package config;

import com.typesafe.config.Config;

public class ConverterConfig {
    public final String schema;
    public final String host;
    public final String path;

    public ConverterConfig(String schema, String host, String path) {
        this.schema = schema;
        this.host = host;
        this.path = path;
    }

    public ConverterConfig(Config config) {
        this(
                config.getString("schema"),
                config.getString("host"),
                config.getString("path")
        );
    }
}
