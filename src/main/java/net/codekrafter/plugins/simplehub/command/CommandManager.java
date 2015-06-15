package net.codekrafter.plugins.simplehub.command;

import java.util.ArrayList;

import net.codekrafter.plugins.simplehub.SimpleHub;
import net.codekrafter.plugins.utils.Parser;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandManager implements CommandExecutor {

	public SimpleHub plugin;
	public static ArrayList<CommandModule> cmds = new ArrayList<CommandModule>();

	public CommandManager(SimpleHub plugin) {
		this.plugin = plugin;
		CommandParse parse = new CommandParse(this);
		cmds.add(parse);
		CommandToggle toggle = new CommandToggle(this);
		cmds.add(toggle);
		CommandSetInv setInv = new CommandSetInv(this);
		cmds.add(setInv);
	}

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String l,
			String[] args) {
		if (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("")
				|| args[0].equalsIgnoreCase(" ") || args[0].equals(null)) {
			s.sendMessage(Parser
					.colorparse("&8Commands For &9Simple Hub&8:"));
			for (CommandModule cmd1 : cmds) {
				String name = cmd1.getName();
				String desc = cmd1.getDesc();
				s.sendMessage(Parser.colorparse("&9" + name + "&8: " + "&8"
						+ desc));
			}
		} else {
			for (CommandModule cmd1 : cmds) {
				if (cmd1.getName().equalsIgnoreCase(args[0])) {
					return cmd1.run(s, cmd, l, args);
				}
			}
		}
		return true;
	}
}
