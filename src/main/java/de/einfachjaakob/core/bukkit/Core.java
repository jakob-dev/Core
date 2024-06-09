package de.einfachjaakob.core.bukkit;

import de.einfachjaakob.core.api.CoreAPI;

import de.einfachjaakob.core.api.config.CoreConfig;
import de.einfachjaakob.core.api.redis.RedisManager;
import de.einfachjaakob.core.api.redis.payloads.HandshakePayload;
import de.einfachjaakob.core.bukkit.listener.RedisListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;


public final class Core extends JavaPlugin {

    private String serverID;
    private CoreAPI coreAPI;
    private RedisManager redisManager;


    @Override
    public void onEnable() {

        serverID = UUID.randomUUID().toString();

        redisManager = new RedisManager("127.0.0.1", 6379);
        redisManager.subscribe(new RedisListener(this, redisManager), "core.global", serverID);
        redisManager.publish("core.global", redisManager.toJson(new HandshakePayload(serverID)));

    }

    public void onConfigReceived(CoreConfig config) {
        coreAPI = new CoreAPI(config);
        coreAPI.initDatabase();
    }

    private void registerCommands() {
    }

    private void registerListeners() {
    }

    public CoreAPI getCoreAPI() {
        return coreAPI;
    }

    public RedisManager getRedisManager() {
        return redisManager;
    }

    public String getServerID() {
        return serverID;
    }
}
