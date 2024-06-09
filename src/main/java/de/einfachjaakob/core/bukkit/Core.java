package de.einfachjaakob.core.bukkit;

import de.einfachjaakob.core.api.CoreAPI;

import de.einfachjaakob.core.api.config.CoreConfig;
import de.einfachjaakob.core.api.redis.RedisManager;
import de.einfachjaakob.core.api.redis.payloads.HandshakePayload;
import de.einfachjaakob.core.bukkit.commands.CoreCommand;
import de.einfachjaakob.core.bukkit.listener.RedisListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;


public final class Core extends JavaPlugin {

    private CoreAPI coreAPI;
    private CoreConfig coreConfig;
    private RedisManager redisManager;


    @Override
    public void onEnable() {


        redisManager = new RedisManager("127.0.0.1", 6379);
        redisManager.subscribe(new RedisListener(this, redisManager), "core.global");

        String coreConfigJson = redisManager.get("core.config");
        if (coreConfigJson != null) {
            coreConfig = (CoreConfig) redisManager.fromJson(coreConfigJson);
            onConfigRetrieved(coreConfig);
        }

    }

    public void onConfigRetrieved(CoreConfig config) {
        coreAPI = new CoreAPI(config);
        coreAPI.initDatabase();

        registerCommands();
        registerListeners();
    }

    private void registerCommands() {
        getCommand("core").setExecutor(new CoreCommand(this));
    }

    private void registerListeners() {
    }

    public CoreAPI getCoreAPI() {
        return coreAPI;
    }

    public RedisManager getRedisManager() {
        return redisManager;
    }


}
