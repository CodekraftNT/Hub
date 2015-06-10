
package net.codekrafter.plugins.simplehub;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.codekrafter.plugins.Game;
import net.codekrafter.plugins.simplehub.command.CommandManager;
import net.codekrafter.plugins.simplehub.events.HubListener;
import net.codekrafter.plugins.simplehub.tasks.SaveTask;
import net.codekrafter.plugins.utils.ColorParser;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;
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
		Game g1 = new Game(new ItemStack(Material.NETHER_STAR),
				"warp ${username} testWarp", "&lTest Game (testWarp)",
				"Warps You To &ltestWarp", true);
		games.add(g1);
		try
		{
			loadFiles();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		//mcstats
		try
		{
			Metrics metrics = new Metrics(this);
			metrics.start();
			getServer().broadcastMessage(ColorParser.parse(prefix + " Started Metrics at http://mcstats.org/plugin/SimpleHub"));
		}
		catch (IOException e)
		{
			getServer().broadcastMessage(ColorParser.parse(prefix + " &4Failed To Submit Stats"));
		}
	}

	/**
	 * @author codekrafter
	 */
	private void loadFiles() throws IOException
	{
		if (!gamesFile.exists())
		{
			gamesFile.createNewFile();
		}
		FileOutputStream gamesOut = new FileOutputStream(gamesFile);
		String s = "";
		for (Game g : games)
		{
			s = s + yaml.dump(g.getContentsMap());
		}
		byte[] bytes = s.getBytes();
		gamesOut.write(bytes);
		gamesOut.close();
	}

	@Override
	public void onDisable()
	{
		saveConfigItems();
		saveConfig();
		String gamesString = "";
		for (Game g : games)
		{
			gamesString = gamesString + yaml.dump(g.getContentsMap());
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
