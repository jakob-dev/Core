package de.einfachjaakob.core.bungee.listener;

import de.einfachjaakob.core.bungee.Core;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerConnectionListener implements Listener {

    private Core plugin;

    public PlayerConnectionListener(Core plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPostLogin(PostLoginEvent event) {
        plugin.getRedisManager().set("core.playercount", ProxyServer.getInstance().getPlayers().size() + "");
    }

}
