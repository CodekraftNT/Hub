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
