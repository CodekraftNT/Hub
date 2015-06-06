package net.codekrafter.plugins.inventory;

import net.codekrafter.plugins.simplehub.SimpleHub;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryListener implements Listener {
	Player p;
	public void onInventoryClickEvent(InventoryClickEvent e) {
		if (e.getWhoClicked().getType() == EntityType.PLAYER) {
			p = (Player) e.getWhoClicked();
			if (SimpleHub.inLobby.contains(p.getUniqueId())) {
				e.setCancelled(true);
			}
		}
	}
}
