
package net.codekrafter.plugins.simplehub;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.codekrafter.plugins.Game;
import net.codekrafter.plugins.simplehub.command.CommandManager;
import net.codekrafter.plugins.simplehub.events.HubListener;
import net.codekrafter.plugins.simplehub.tasks.SaveTask;
import net.codekrafter.plugins.simplehub.yamlhelper.CustomYML;
import net.codekrafter.plugins.utils.Parser;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;
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
	public static String prefix = Parser.colorparse("&9Simple Hub>&8");
	public static List<String> inHub = new ArrayList<String>();
	public SaveTask saveTask = new SaveTask(this);
	public static Inventory hubInv = Bukkit.createInventory(null,
			InventoryType.PLAYER);
	public static Yaml yaml = new Yaml();
	public static List<Game> games = new ArrayList<Game>();
	public CustomYML gamesFile = new CustomYML(this, "games");
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

		// mcstats
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
		gamesFile.saveCustomConfig();
	}

	private void saveConfigItems()
	{
		config.set("inHub", inHub);
		for (Game g : games)
		{

			Map<String, Map<String, Object>> map = g.getContentsMap();
			String name = g.getName();
			gamesConfig.set("games." + name + ".other.command", map
					.get("other").get("command"));
			gamesConfig.set("games." + name + ".item", map.get("item"));
		}
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

		ConfigurationSection gamesConfig1 = gamesConfig
				.getConfigurationSection("games");
		Map<String, Object> values = gamesConfig1.getValues(false);
		List<MemorySection> memsecs = new ArrayList<MemorySection>();
		for (String s : values.keySet())
		{
			MemorySection memsec = (MemorySection) values.get(s);
			memsecs.add(memsec);
		}
		for (MemorySection memsec : memsecs)
		{
			MemorySection issec = (MemorySection) memsec.get("item");
			Map<String, Object> ismap = issec.getValues(false);
			ItemStack is = ItemStack.deserialize(ismap);
			Game g = new Game(
					is, memsec.getString("other.command"),
					memsec.getName());
			games.add(g);
		}
	}

	private void addDefaults()
	{
		config.addDefault("prefix", "&9Simple Hub>");
		config.addDefault("inHub", new ArrayList<String>());
	}
}
