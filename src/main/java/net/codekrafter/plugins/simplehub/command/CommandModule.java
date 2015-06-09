package net.codekrafter.plugins.simplehub.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface CommandModule {

	
	public boolean run(CommandSender s, Command cmd, String l, String[] args);
	public String getName();
	public String getDesc();
}
