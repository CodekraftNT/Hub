
package net.codekrafter.plugins.simplehub;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.codekrafter.plugins.Game;
import net.codekrafter.plugins.simplehub.command.CommandManager;
import net.codekrafter.plugins.simplehub.events.HubListener;
import net.codekrafter.plugins.simplehub.tasks.SaveTask;
import net.codekrafter.plugins.utils.ColorParser;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.yaml.snakeyaml.Yaml;

public class SimpleHub extends JavaPlugin
{

	public FileConfiguration config = getConfig();
	public static String prefix = ColorParser.parse("&9Simple Hub>&8");
	public static List<String> inHub = new ArrayList<String>();
	public SaveTask saveTask = new SaveTask(this);
	public static Inventory hubInv = Bukkit.createInventory(null,
			InventoryType.PLAYER);
	public static Yaml yaml = new Yaml();
	public static List<Game> games = new ArrayList<Game>();
	public File gamesFile = new File(getDataFolder(), "games.yml");

	@Override
	public void onEnable()
	{
		manageConfig();
		getServer().getPluginManager().registerEvents(new HubListener(), this);
		getServer().getPluginCommand("sh")
				.setExecutor(new CommandManager(this));
		List<String> commands = new ArrayList<String>();
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
		if (!gamesFile.exists())
		{
			try
			{
				gamesFile.createNewFile();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onDisable()
	{
		saveConfigItems();
		saveConfig();
		String gamesString = "";
		for (Game g : games)
		{
			gamesString = gamesString + yaml.dump(g.getMap());
		}

	}

	public void saveTheConfig()
	{
		saveConfigItems();
		saveConfig();
	}

	private void saveConfigItems()
	{
		config.set("inHub", inHub);

	}

	private void manageConfig()
	{
		addDefaults();
		config.options().copyDefaults(true);
		config.options()
				.header("#The Simple Hub Config File\n#Remember, Dont Edit Something If You Don't Know What It Does");
		saveConfig();
		getValues();
	}

	@SuppressWarnings("unchecked")
	private void getValues()
	{
		prefix = ColorParser.parse(config.getString("prefix") + "&8");
		inHub = (List<String>) config.getList("inHub");

	}

	private void addDefaults()
	{
		config.addDefault("prefix", "&9Simple Hub>");
		config.addDefault("inHub", new ArrayList<String>());
	}
}
