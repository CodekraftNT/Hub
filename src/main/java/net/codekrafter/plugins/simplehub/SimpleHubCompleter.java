/**
 * @author codekrafter
 * @since Jun 9, 2015
 * @version 1.0
 */

package net.codekrafter.plugins.simplehub;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.codekrafter.plugins.simplehub.command.CommandManager;
import net.codekrafter.plugins.simplehub.command.CommandModule;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

/**
 * @author codekrafter
 *
 */
public class SimpleHubCompleter implements TabCompleter
{

	/**
	 * @author codekrafter
	 * @see org.bukkit.command.TabCompleter#onTabComplete(org.bukkit.command.CommandSender,
	 *      org.bukkit.command.Command, java.lang.String, java.lang.String[])
	 */
	@Override
	public List<String> onTabComplete(CommandSender s, Command cmd, String a,
			String[] args)
	{
		ArrayList<String> possible = new ArrayList<String>();
		if (args.length == 1)
		{
			for (CommandModule cm : CommandManager.cmds)
			{
				if (!possible.contains(cm.getName().toLowerCase()))
				{
					possible.add(cm.getName().toLowerCase());
				}
			}
		}
		Collections.sort(possible);
		return possible;
	}

}
