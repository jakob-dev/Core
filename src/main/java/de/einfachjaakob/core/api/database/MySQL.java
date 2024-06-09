package de.einfachjaakob.core.api.database;

import de.einfachjaakob.core.api.config.CoreConfig;
import de.einfachjaakob.core.api.database.utils.DatabaseSettings;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MySQL {

    private ConnectionManager connectionManager;
    private ExecutorService threadPool;

    public MySQL() {
        threadPool = Executors.newFixedThreadPool(10);
    }

    public MySQL(int maxThreads) {
        threadPool = Executors.newFixedThreadPool(maxThreads);
    }

    public ConnectionManager getConnectionManager() {
        return connectionManager;
    }

    public ExecutorService getThreadPool() {
        return threadPool;
    }

    public boolean connect(String host, String port, String username, String password, String database) {
        connectionManager = new ConnectionManager(host, port, username, password, database);
        return connectionManager.open();
    }

    public boolean connect(String host, String port, String username, String password, String database, int connectionTimeout, int maxPoolSize) {
        connectionManager = new ConnectionManager(host, port, username, password, database, connectionTimeout, maxPoolSize);
        return connectionManager.open();
    }

    public boolean connect(CoreConfig coreConfig) {
        connectionManager = new ConnectionManager(coreConfig.getDatabaseHost(), coreConfig.getDatabasePort(),
                coreConfig.getDatabaseUsername(), coreConfig.getDatabasePassword(), coreConfig.getDatabaseName(),
                coreConfig.getDatabaseTimeout(), coreConfig.getDatabasePoolSize());
        return connectionManager.open();
    }

    public void disconnect() {
        if (connectionManager != null)
            connectionManager.close();

        if (threadPool != null)
            threadPool.shutdown();
    }
}
