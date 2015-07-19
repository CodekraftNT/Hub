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

package net.codekrafter.plugins.simplehub;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.codekrafter.plugins.Game;
import net.codekrafter.plugins.simplehub.command.CommandManager;
import net.codekrafter.plugins.simplehub.events.HubListener;
import net.codekrafter.plugins.simplehub.tasks.SaveTask;
import net.codekrafter.plugins.utils.Parser;
import net.codekrafter.plugins.utils.YamlManager;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;
import org.yaml.snakeyaml.Yaml;

public class SimpleHub extends JavaPlugin
{

	public FileConfiguration config = getConfig();
	public static String prefix = Parser.colorparse("&9Hub>&8");
	public static List<String> inHub = new ArrayList<String>();
	public SaveTask saveTask = new SaveTask(this);
	public static Inventory hubInv = Bukkit.createInventory(null,
			InventoryType.PLAYER);
	public static Yaml yaml = new Yaml();
	public static List<Game> games = new ArrayList<Game>();
	public static ItemStack[] pvpArmor;
	public YamlManager gamesFile = new YamlManager(this, "games");
	FileConfiguration gamesConfig = gamesFile.getCustomConfig();

	@Override
	public void onEnable()
	{
		ItemStack g1is = new ItemStack(Material.NETHER_STAR);
		ItemMeta g1ismeta = g1is.getItemMeta();
		g1ismeta.setDisplayName(Parser.colorparse("&f&lTest Game"));
		List<String> g1islore = new ArrayList<String>();
		g1islore.add(Parser.colorparse("&rTells You &ltestgame"));
		g1ismeta.setLore(g1islore);
		g1is.setItemMeta(g1ismeta);
		// g1is.addEnchantment(Enchantment.getByName(""), 1);
		Game g1 = new Game(g1is, "tell ${username} testGame", "testGame");
		games.add(g1);
		manageConfig();
		getServer().getPluginManager().registerEvents(new HubListener(), this);
		getServer().getPluginCommand("sh")
				.setExecutor(new CommandManager(this));
		List<String> commands = new ArrayList<String>();
		// Updater updater = new Updater(this, TODO DBO, this.getFile(),
		// Updater.UpdateType.DEFAULT, false);
		commands.add("sh");
		commands.add("simplehub");
		commands.add("simpleh");
		commands.add("shub");
		for (String s : commands)
		{
			getServer().getPluginCommand(s).setExecutor(
					new CommandManager(this));
			getServer().getPluginCommand(s).setTabCompleter(
					new SimpleHubCompleter());
		}
		SaveTask st = new SaveTask(this);
		st.runTaskTimer(this, 0, 60 * 20 * 5);
		/*
		 * TickTask tt = new TickTask(); tt.runTaskTimer(this, 0, 0);
		 */

		try
		{
			Metrics metrics = new Metrics(this);
			metrics.start();
			getLogger().info(
					"Started Metrics at http://mcstats.org/plugin/SimpleHub");
		}
		catch (IOException e)
		{
			getLogger().info("Failed To Submit Stats");
			e.printStackTrace();
		}
	}

	@Override
	public void onDisable()
	{
		saveConfigItems();
		saveConfig();

	}

	public void saveTheConfig()
	{
		saveConfigItems();
		saveConfig();
	}

	private void saveConfigItems()
	{
		config.set("inHub", inHub);
		gamesFile.saveCustomConfig();

	}

	private void manageConfig()
	{
		getValues();
		addDefaults();
		config.options().copyDefaults(true);
		config.options()
				.header("The Simple Hub Config File\nRemember, Dont Edit Something If You Don't Know What It Does");
		saveConfig();
		gamesConfig
				.options()
				.header("The Simple Hub Games Config File\nRemember, Dont Edit Something If You Don't Know What It Does");
		gamesFile.saveCustomConfig();
	}

	@SuppressWarnings("unchecked")
	private void getValues()
	{
		prefix = Parser.colorparse(config.getString("prefix") + "&8");
		inHub = (List<String>) config.getList("inHub");
		ItemStack defHelm = new ItemStack(Material.IRON_HELMET);
		ItemStack defChest = new ItemStack(Material.IRON_CHESTPLATE);
		ItemStack defPants = new ItemStack(Material.IRON_LEGGINGS);
		ItemStack defBoots = new ItemStack(Material.IRON_BOOTS);
		ItemStack helm = config.getItemStack("pvp.armor.helm", defHelm);
		ItemStack chest = config.getItemStack("pvp.armor.chest", defChest);
		ItemStack pants = config.getItemStack("pvp.armor.pants", defPants);
		ItemStack boots = config.getItemStack("pvp.armor.boots", defBoots);
		pvpArmor = new ItemStack[4];
		pvpArmor[0] = helm;
		pvpArmor[1] = chest;
		pvpArmor[2] = pants;
		pvpArmor[3] = boots;
	}

	private void addDefaults()
	{
		config.addDefault("prefix", "&9Hub>&8");
	}
}
