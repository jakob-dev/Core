package de.einfachjaakob.core.bungee;

import de.einfachjaakob.core.api.CoreAPI;
import de.einfachjaakob.core.api.config.CoreConfig;
import de.einfachjaakob.core.api.redis.RedisManager;
import de.einfachjaakob.core.bungee.listener.PlayerConnectionListener;
import de.einfachjaakob.core.bungee.listener.RedisListener;
import de.einfachjaakob.core.bungee.utils.ConfigManager;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;

public class Core extends Plugin {

    private ConfigManager configManager;

    private RedisManager redisManager;

    private CoreConfig coreConfig;
    private CoreAPI coreAPI;

    @Override
    public void onEnable() {

        configManager = new ConfigManager(this);
        Configuration config = configManager.getConfig("config.yml");
        coreConfig = new CoreConfig();
        coreConfig.load(config);

        redisManager = new RedisManager("127.0.0.1", 6379);
        redisManager.subscribe(new RedisListener(this, redisManager), "core.global");
        redisManager.set("core.config", redisManager.toJson(coreConfig));
        redisManager.set("core.maxplayers", getProxy().getConfigurationAdapter().getListeners().iterator().next().getMaxPlayers() + "");

        coreAPI = new CoreAPI(coreConfig);
        coreAPI.initDatabase();

        registerEvents();
    }

    public void registerEvents() {
        getProxy().getPluginManager().registerListener(this, new PlayerConnectionListener(this));
    }


    public CoreAPI getCoreAPI() {
        return coreAPI;
    }

    public RedisManager getRedisManager() {
        return redisManager;
    }
}
