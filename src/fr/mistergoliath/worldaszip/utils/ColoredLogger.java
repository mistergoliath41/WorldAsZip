package fr.mistergoliath.worldaszip.utils;

import org.bukkit.Bukkit;

public class ColoredLogger {
	
	public ColoredLogger info(String message) {
		Bukkit.getServer().getConsoleSender().sendMessage(message.replace("&", "§"));
		return this;
	}
	
}
