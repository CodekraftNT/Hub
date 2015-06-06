package net.codekrafter.plugins.utils;

import org.bukkit.ChatColor;

public class ColorParser {

	public static String parse(String s) {
		
		return ChatColor.translateAlternateColorCodes("&".toCharArray()[0], s);

	};
}
