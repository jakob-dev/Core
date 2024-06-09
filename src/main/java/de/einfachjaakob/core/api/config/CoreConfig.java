package de.einfachjaakob.core.api.config;

import de.einfachjaakob.core.api.redis.payloads.RedisPayload;
import net.md_5.bungee.config.Configuration;

public class CoreConfig extends RedisPayload {

    private String databaseHost;
    private String databasePort;
    private String databaseUsername;
    private String databasePassword;
    private String databaseName;
    private int databaseTimeout;
    private int databasePoolSize;


    public CoreConfig() {
        this.name = "config";
    }

    public void load(Configuration config) {
        databaseHost = config.getString("Database.host");
        databasePort = config.getString("Database.port");
        databaseUsername = config.getString("Database.username");
        databasePassword = config.getString("Database.password");
        databaseName = config.getString("Database.database");
        databaseTimeout = config.getInt("Database.connection-timeout");
        databasePoolSize = config.getInt("Database.max-pool-size");
    }


    public String getDatabaseHost() {
        return databaseHost;
    }

    public String getDatabasePort() {
        return databasePort;
    }

    public String getDatabaseUsername() {
        return databaseUsername;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public int getDatabaseTimeout() {
        return databaseTimeout;
    }

    public int getDatabasePoolSize() {
        return databasePoolSize;
    }
}
