package de.einfachjaakob.core.api.redis;

import com.google.gson.Gson;
import de.einfachjaakob.core.api.config.CoreConfig;
import de.einfachjaakob.core.api.redis.payloads.HandshakePayload;
import de.einfachjaakob.core.api.redis.payloads.RedisPayload;
import org.json.JSONObject;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;


public class RedisManager {

    private Gson gson = new Gson();
    private JedisPool jedis;


    public RedisManager(String host, int port) {
        jedis = new JedisPool(host, port);
    }


    public void subscribe(JedisPubSub redisListener, String... channels) {
        new Thread(() -> jedis.getResource().subscribe(redisListener, channels)).start();
    }


    public void publish(String channel, String message) {
        try (Jedis publisher = jedis.getResource()) {
            publisher.publish(channel, message);
        }
    }

    public JedisPool getJedis() {
        return jedis;
    }

    public String toJson(RedisPayload payload) {

        String json = gson.toJson(payload);
        JSONObject obj = new JSONObject();
        obj.put("name", payload.name);
        obj.put("payload", json);

        return obj.toString();
    }

    public RedisPayload fromJson(String json) {

        JSONObject obj = new JSONObject(json);
        String name = obj.getString("name");
        String payloadJson = obj.getString("payload");

        switch (name) {
            case "handshake":
                return gson.fromJson(payloadJson, HandshakePayload.class);

            case "config":
                return gson.fromJson(payloadJson, CoreConfig.class);

            default:
                return null;

        }
    }


}
