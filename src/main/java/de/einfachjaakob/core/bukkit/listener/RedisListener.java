package de.einfachjaakob.core.bukkit.listener;

import de.einfachjaakob.core.api.config.CoreConfig;
import de.einfachjaakob.core.api.redis.RedisManager;
import de.einfachjaakob.core.api.redis.payloads.RedisPayload;
import de.einfachjaakob.core.bukkit.Core;
import redis.clients.jedis.JedisPubSub;

public class RedisListener extends JedisPubSub {

    private Core plugin;
    private RedisManager redisManager;

    public RedisListener(Core plugin, RedisManager redisManager) {
        this.plugin = plugin;
        this.redisManager = redisManager;
    }

    @Override
    public void onMessage(String channel, String message) {

        if (channel.equalsIgnoreCase(plugin.getServerID())) {

            RedisPayload payload = redisManager.fromJson(message);

            if (payload instanceof CoreConfig) {

                CoreConfig coreConfig = (CoreConfig) payload;
                plugin.onConfigReceived(coreConfig);

            }
        }
    }

}
