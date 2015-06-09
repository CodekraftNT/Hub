/**
 * 
 */
package net.codekrafter.plugins.simplehub.command;

import net.codekrafter.plugins.simplehub.SimpleHub;
import net.codekrafter.plugins.utils.ColorParser;

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
