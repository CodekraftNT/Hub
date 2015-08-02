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
 * @author codekrafter
 * @since Jun 9, 2015
 * @version 1.0
 */

package net.codekrafter.plugins.simplehub;

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
