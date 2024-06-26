package de.einfachjaakob.core.bungee.listener;

import de.einfachjaakob.core.api.redis.RedisManager;
import de.einfachjaakob.core.api.redis.payloads.HandshakePayload;
import de.einfachjaakob.core.api.redis.payloads.RedisPayload;
import de.einfachjaakob.core.bungee.Core;
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

        if (channel.equalsIgnoreCase("core.global")) {
            RedisPayload payload = redisManager.fromJson(message);
        }
    }

}
