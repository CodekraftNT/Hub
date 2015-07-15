/*
 * 	SimpleHub
 * 	The Hub Plugin For Codekraft
 *
 *     Copyright (C) 2015  codekrafter
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Lesser License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Lesser License for more details.
 *
 *     You should have received a copy of the GNU General Lesser License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.codekrafter.plugins.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Parser {

	public static String colorparse(String s) {
		
		return ChatColor.translateAlternateColorCodes("&".toCharArray()[0], s);

	};
	
public static String strip(String s) {
		
		return ChatColor.stripColor(s);

	};
	
public static String commandparse(String s, Player p) {
	String newS = "";
	newS = s.replaceAll("(\\$\\{username\\})", p.getName());
	newS = newS.replaceAll("(\\$\\{displayname\\})", p.getDisplayName());
	newS = newS.replaceAll("(\\$\\{uuid\\})", p.getUniqueId().toString());
	return newS;
}
}
