package de.einfachjaakob.core.bukkit.commands;

import de.einfachjaakob.core.bukkit.Core;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

public class CoreCommand implements CommandExecutor {

    private Core plugin;

    public CoreCommand(Core plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {


        if (!commandSender.hasPermission("core.core")) {
            return false;
        }

        switch (args.length) {
            case 0:

                PluginDescriptionFile pluginDescriptionFile = plugin.getDescription();
                boolean isDatabaseRunning = !plugin.getCoreAPI().getDatabase().getConnectionManager().isClosed();

                commandSender.sendMessage("§bCore §7» running version: §b" + pluginDescriptionFile.getVersion());
                commandSender.sendMessage("§bCore §7» database status: " + (isDatabaseRunning ? "§aoperational" : "§coffline"));

                break;
            default:
                break;
        }


        return false;
    }
}
