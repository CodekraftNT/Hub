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

import java.util.ArrayList;

import net.codekrafter.plugins.simplehub.SimpleHub;
import net.codekrafter.plugins.simplehub.events.HubListener;
import net.codekrafter.plugins.simplehub.events.LobbyPvPStatusChangeEvent;
import net.codekrafter.plugins.utils.Parser;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandManager implements CommandExecutor
{

	public SimpleHub plugin;
	public static ArrayList<CommandModule> cmds = new ArrayList<CommandModule>();

	public CommandManager(SimpleHub plugin)
	{
		this.plugin = plugin;
		CommandParse parse = new CommandParse(this);
		cmds.add(parse);
		CommandToggle toggle = new CommandToggle(this);
		cmds.add(toggle);
		ArrayList<CommandModule> cmds0 = new ArrayList<CommandModule>();
		for (CommandModule c : cmds)
		{
			if (!cmds0.contains(c))
			{
				cmds0.add(c);
			}
		}
		cmds = cmds0;
	}

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String l,
			String[] args)
	{
		if (l.equalsIgnoreCase("pvp"))
		{
			if (s instanceof Player)
			{
				Player p = (Player) s;
				if (HubListener.inpvp.contains(p.getName()))
				{
					Bukkit.getServer().getPluginManager().callEvent(new LobbyPvPStatusChangeEvent(p, false));
				}
				else
				{
					Bukkit.getServer().getPluginManager().callEvent(new LobbyPvPStatusChangeEvent(p, true));
				}
			}
			else
			{
				s.sendMessage("§4Only Players Can Toggle Lobby PvP");
			}
			return true;
		}
		else if (args.length == 0 || args[0] == null
				|| args[0].equalsIgnoreCase("help")
				|| args[0].equalsIgnoreCase("")
				|| args[0].equalsIgnoreCase(" "))
		{
			s.sendMessage(Parser.colorparse("&8Commands For &9Simple Hub&8:"));
			for (CommandModule cmd1 : cmds)
			{
				String name = cmd1.getName();
				String desc = cmd1.getDesc();
				s.sendMessage(Parser.colorparse("&9" + name + "&8: " + "&8"
						+ desc));
			}
		}
		else
		{
			for (CommandModule cmd1 : cmds)
			{
				if (cmd1.getName().equalsIgnoreCase(args[0]))
				{
					return cmd1.run(s, cmd, l, args);
				}
			}
		}
		return true;
	}
}
