package net.codekrafter.plugins.simplehub.inventory;

import java.util.HashMap;

import net.codekrafter.plugins.simplehub.SimpleHub;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class HubListener implements Listener {
	@EventHandler
	public void InventoryClick(InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player) {
			Player p = (Player) e.getWhoClicked();
			if (SimpleHub.inLobby.contains(p.getUniqueId().toString())) {
				e.setCancelled(true);
				p.updateInventory();
			}
		}
	}

	@EventHandler
	public void ItemDrop(PlayerDropItemEvent e) {
		Player p = e.getPlayer();
		PlayerInventory i = p.getInventory();
		ItemStack[] c = i.getContents();
		if (SimpleHub.inLobby.contains(p.getUniqueId().toString())) {
			e.setCancelled(true);
			p.getInventory().setContents(c);
		}
	}

	@EventHandler
	public void BlockBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		if (SimpleHub.inLobby.contains(p.getUniqueId().toString())) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void BlockPlace(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		if (SimpleHub.inLobby.contains(p.getUniqueId().toString())) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void FoodChange(FoodLevelChangeEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if (SimpleHub.inLobby.contains(p.getUniqueId().toString())) {
				e.setCancelled(true);
				p.updateInventory();
			}
		}
	}

	HashMap<Player, Boolean> cooldown = new HashMap<Player, Boolean>();
	
	@EventHandler
	public void PlayerMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (SimpleHub.inLobby.contains(p.getUniqueId().toString()) && !(cooldown.containsKey(p))) {
			
		}
	}
}
