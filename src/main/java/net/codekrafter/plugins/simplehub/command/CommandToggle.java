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
package net.codekrafter.plugins.simplehub.command;

import net.codekrafter.plugins.simplehub.SimpleHub;
import net.codekrafter.plugins.utils.Parser;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandToggle implements CommandModule {

	public CommandManager cm;
	public SimpleHub plugin;
	private String name = "Toggle";
	private String desc = "Toggles wether or not a player is in the hub!";
	private String usage = "&8toggle <player>";

	public CommandToggle(CommandManager cm) {
		this.cm = cm;
		this.plugin = cm.plugin;
	}

	@Override
	public boolean run(CommandSender s, Command cmd, String l, String[] args) {
		if (args.length == 1 || args[1] == null || args[1] == "" || args[1] == " ") {
			s.sendMessage(Parser.colorparse("&8/" + cmd.getName()  + " " + usage));
			return true;
		}
		Player p = Bukkit.getPlayer(args[1]);
		if(SimpleHub.inHub.contains(p.getUniqueId().toString())) {
			SimpleHub.inHub.remove(p.getUniqueId().toString());
			s.sendMessage(SimpleHub.prefix + Parser.colorparse(" Successfully Toggled " + p.getName() + "'s Hub Status Off"));
		} else {
			SimpleHub.inHub.add(p.getUniqueId().toString());
			s.sendMessage(SimpleHub.prefix + Parser.colorparse(" Successfully Toggled " + p.getName() + "'s Hub Status On"));
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
