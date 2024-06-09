package de.einfachjaakob.core.api.redis.payloads;

public class HandshakePayload extends RedisPayload {

    private String serverID;

    public HandshakePayload(String serverID) {
        this.serverID = serverID;
        this.name = "handshake";
    }

    public String getServerID() {
        return serverID;
    }
}
