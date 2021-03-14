package config;

import com.typesafe.config.Config;

public class ServerConfig {
    public final int port;

    public ServerConfig(int port) {
        this.port = port;
    }

    public ServerConfig(Config config) {
        this(config.getInt("port"));
    }
}
