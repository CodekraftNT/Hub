/**
 * @author codekrafter
 * @since Jun 9, 2015
 * @version 1.0
 */

package net.codekrafter.plugins;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.inventory.ItemStack;

/**
 * @author codekrafter
 *
 */
public class Game
{

	/**
	 * @author codekrafter
	 */
	public Game(ItemStack is, String command, String name)
	{
		this.is = is;
		this.command = command;
		this.name = name;
	}

	private Map<String, Map<String, Object>> map = new HashMap<String, Map<String, Object>>();
	private ItemStack is;
	private String command;
	private String name;
	private Map<String, Object> other = new HashMap<String, Object>();

	public Map<String, Map<String, Object>> getContentsMap()
	{
		other.put("command", this.command);
		map.put("other", other);
		map.put("item", is.serialize());
		return map;
	}

	public static Game makeFromContentsMap(
			Map<String, Map<String, Object>> map, String name)
	{
		Map<String, Object> othermap = map.get("other");
		ItemStack is = ItemStack.deserialize(map.get("item"));
		String command = (String) othermap.get("command");
		return new Game(is, command, name);
	}

	public String getCommand()
	{
		return command;
	}

	public void setCommand(String command)
	{
		this.command = command;
	}

	public ItemStack getIs()
	{
		return is;
	}

	public void setIs(ItemStack is)
	{
		this.is = is;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}
