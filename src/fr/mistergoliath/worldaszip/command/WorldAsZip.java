package fr.mistergoliath.worldaszip.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.mistergoliath.worldaszip.Main;

public class WorldAsZip implements CommandExecutor {
    private Main plugin;

    public WorldAsZip(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
    	if (cs instanceof Player) {
    		Player p = (Player)cs;
            if (p.isOp() || p.hasPermission("worldaszip.use")) {
                p.sendMessage("§eBuilding zip...");
                plugin.zip((p.getLocation().getWorld()));
            } else {
                p.sendMessage("§cYou don't have permission !");
            }
    	}
        return true;
    }
}
