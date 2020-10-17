package ru.dorokhov.sd.refactoring.config;

import java.util.Objects;
import java.util.Properties;

public class ApplicationConfig {
    private final String db;
    private final int port;

    public ApplicationConfig(final Properties props) {
        this.db = props.getProperty("db");
        this.port = Integer.parseInt(props.getProperty("port"));
    }

    public String getDb() {
        return db;
    }

    public int getPort() {
        return port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApplicationConfig that = (ApplicationConfig) o;
        return port == that.port &&
                Objects.equals(db, that.db);
    }

    @Override
    public int hashCode() {
        return Objects.hash(db, port);
    }
}
