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
package net.codekrafter.plugins.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class YamlManager
{

	public static JavaPlugin plugin;
	public File path;
	private FileConfiguration customConfig = null;
	private File customConfigFile = null;
	private String name;

	public YamlManager(JavaPlugin plugin, String name)
	{
		YamlManager.plugin = plugin;
		this.name = name;
	}
	
	public YamlManager(JavaPlugin plugin, String name, String path, boolean relitiveToDataFolder)
	{
		YamlManager.plugin = plugin;
		this.name = name;
		if(relitiveToDataFolder) {
			this.path = new File(plugin.getDataFolder(), path);
		} else {
			this.path = new File(path);
		}
	}

	public void reloadCustomConfig()
	{
		if (customConfigFile == null)
		{
			customConfigFile = new File(path, name + ".yml");
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
			customConfigFile = new File(path, name + ".yml");
		}
		if (!customConfigFile.exists())
		{
			plugin.saveResource(name + ".yml", false);
		}
	}

}
