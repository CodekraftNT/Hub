package net.codekrafter.plugins.simplehub.tasks;

import net.codekrafter.plugins.simplehub.SimpleHub;
import net.codekrafter.plugins.utils.ColorParser;

import org.bukkit.scheduler.BukkitRunnable;

public class SaveTask extends BukkitRunnable {
	private SimpleHub plugin;

	public SaveTask(SimpleHub plugin) {
		this.plugin = plugin;
	}

	@Override
	public void run() {
		plugin.getServer().broadcastMessage(
				plugin.prefix + " " + ColorParser.parse("&aSimple Hub Save Commencing...."));
		plugin.saveTheConfig();
	}
}
