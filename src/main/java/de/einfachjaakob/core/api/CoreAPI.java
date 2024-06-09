package de.einfachjaakob.core.api;

import de.einfachjaakob.core.api.config.CoreConfig;
import de.einfachjaakob.core.api.database.MySQL;

public class CoreAPI {

    private MySQL database;

    private CoreConfig coreConfig;

    public CoreAPI(CoreConfig coreConfig) {
        this.coreConfig = coreConfig;
    }

    public void initDatabase() {
        if (database == null) {
            database = new MySQL();
            database.connect(coreConfig);
        }
    }

    public MySQL getDatabase() {
        return database;
    }

}
