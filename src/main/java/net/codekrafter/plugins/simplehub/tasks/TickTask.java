package net.codekrafter.plugins.simplehub.tasks;

import net.codekrafter.plugins.simplehub.events.TickEvent;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class TickTask extends BukkitRunnable {

	@Override
	public void run() {
		TickEvent e = new TickEvent();
		Bukkit.getServer().getPluginManager().callEvent(e);
	}

}
