
package net.codekrafter.plugins.simplehub.yaml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import net.codekrafter.plugins.simplehub.SimpleHub;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class YamlManager
{

	public static SimpleHub plugin;

	public YamlManager(SimpleHub plugin, String name)
	{
		YamlManager.plugin = plugin;
		this.name = name;
	}

	private FileConfiguration customConfig = null;
	private File customConfigFile = null;
	private String name;

	public void reloadCustomConfig()
	{
		if (customConfigFile == null)
		{
			customConfigFile = new File(plugin.getDataFolder(), name + ".yml");
		}

		customConfig = YamlConfiguration.loadConfiguration(customConfigFile);
		InputStream defConfigStream = plugin.getResource(name + ".yml");
		if (defConfigStream != null)
		{
			YamlConfiguration defConfig = YamlConfiguration
					.loadConfiguration(defConfigStream);
			customConfig.setDefaults(defConfig);
		}
	}

	public FileConfiguration getCustomConfig()
	{
		if (customConfig == null)
		{
			reloadCustomConfig();
		}
		return customConfig;
	}

	public void saveCustomConfig()
	{
		if (customConfig == null || customConfigFile == null)
		{
			return;
		}
		try
		{
			getCustomConfig().save(customConfigFile);
		}
		catch (IOException ex)
		{
			plugin.getLogger().log(Level.SEVERE,
					"Could not save config to " + customConfigFile, ex);
		}
	}

	public void saveDefaultConfig()
	{
		if (customConfigFile == null)
		{
			customConfigFile = new File(plugin.getDataFolder(), name + ".yml");
		}
		if (!customConfigFile.exists())
		{
			plugin.saveResource(name + ".yml", false);
		}
	}

}
