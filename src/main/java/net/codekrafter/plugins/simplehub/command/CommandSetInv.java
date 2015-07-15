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
/**
 * 
 */
package net.codekrafter.plugins.simplehub.command;

import net.codekrafter.plugins.simplehub.SimpleHub;
import net.codekrafter.plugins.utils.Parser;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * @author codekrafter
 *
 */
public class CommandSetInv implements CommandModule {

	private String name = "SetInv";
	private String desc = "Set The Hub Inventory";
	private String usage = "setinv";
	private CommandManager cm;

	/**
	 * @author codekrafter
	 * @param commandManager
	 */
	public CommandSetInv(CommandManager cm)
	{
		this.cm = cm;
	}

	@Override
	public boolean run(CommandSender s, Command cmd, String l, String[] args) {
		if (s instanceof Player) {
			Player p = (Player) s;
			SimpleHub.hubInv = p.getInventory();
		}
		return true;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDesc() {

		return desc;
	}

}
