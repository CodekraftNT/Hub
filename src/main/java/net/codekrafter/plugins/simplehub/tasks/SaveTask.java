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
package net.codekrafter.plugins.simplehub.tasks;

import net.codekrafter.plugins.simplehub.SimpleHub;
import net.codekrafter.plugins.utils.Parser;

import org.bukkit.scheduler.BukkitRunnable;

public class SaveTask extends BukkitRunnable {
	private SimpleHub plugin;

	public SaveTask(SimpleHub plugin) {
		this.plugin = plugin;
	}

	@Override
	public void run() {
		plugin.getServer().broadcastMessage(
				SimpleHub.prefix + " " + Parser.colorparse("&8Simple Hub Save Commencing...."));
		plugin.saveTheConfig();
	}
}
