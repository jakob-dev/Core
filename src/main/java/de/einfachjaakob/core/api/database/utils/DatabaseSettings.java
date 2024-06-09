package de.einfachjaakob.core.api.database.utils;

import net.md_5.bungee.config.Configuration;
import org.bukkit.configuration.file.FileConfiguration;

public class DatabaseSettings {

    private String host;
    private String port;
    private String username;
    private String password;
    private String database;
    private int connectionTimeout;
    private int maxPoolSize;

    public DatabaseSettings(FileConfiguration fileConfiguration) {

        this.host = fileConfiguration.getString("Database.host");
        this.port = fileConfiguration.getString("Database.port");
        this.username = fileConfiguration.getString("Database.username");
        this.password = fileConfiguration.getString("Database.password");
        this.database = fileConfiguration.getString("Database.database");
        this.connectionTimeout = fileConfiguration.getInt("Database.connection-timeout");
        this.maxPoolSize = fileConfiguration.getInt("Database.max-pool-size");

    }

    public DatabaseSettings(Configuration configuration) {

        this.host = configuration.getString("Database.host");
        this.port = configuration.getString("Database.port");
        this.username = configuration.getString("Database.username");
        this.password = configuration.getString("Database.password");
        this.database = configuration.getString("Database.database");
        this.connectionTimeout = configuration.getInt("Database.connection-timeout");
        this.maxPoolSize = configuration.getInt("Database.max-pool-size");

    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDatabase() {
        return database;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }
}
