/**
 * @author codekrafter
 * @since Jun 9, 2015
 * @version 1.0
 */

package net.codekrafter.plugins;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * @author codekrafter
 *
 */
public class Game
{

	/**
	 * @author codekrafter
	 */
	public Game(ItemStack is, String command, String name, String lore,
			boolean enchanted)
	{
		this.is = is;
		this.command = command;
		this.name = name;
		this.lore = lore;
		ItemMeta meta = this.is.getItemMeta();
		meta.setDisplayName(name);
		List<String> loreList = new ArrayList<String>();
		loreList.add(lore);
		meta.setLore(loreList);
		this.is.setItemMeta(meta);
	}

	private Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
	private ItemStack is;
	private String command;
	private String name;
	private String lore;
	private Map<String, String> itemstackMap = new HashMap<String, String>();
	private Map<String, String> other = new HashMap<String, String>();
	private boolean enchanted;

	public Map<String, Map<String, String>> getContentsMap()
	{
		itemstackMap.put("name", this.name);
		itemstackMap.put("lore", this.lore);
		itemstackMap.put("enchanted", String.valueOf(this.enchanted));
		itemstackMap.put("type", this.is.getType().name());
		other.put("command", this.command);
		map.put("other", other);
		map.put("itemstack", itemstackMap);
		return map;
	}
}
