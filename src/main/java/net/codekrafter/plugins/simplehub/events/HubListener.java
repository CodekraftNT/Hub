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

package net.codekrafter.plugins.simplehub.events;

import java.util.ArrayList;
import java.util.List;

import net.codekrafter.plugins.simplehub.Game;
import net.codekrafter.plugins.simplehub.ServerFinder;
import net.codekrafter.plugins.simplehub.SimpleHub;
import net.codekrafter.plugins.utils.Parser;
import net.md_5.bungee.api.config.ServerInfo;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.EntityEffect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class HubListener implements Listener
{

	SimpleHub pl;

	public HubListener(SimpleHub plugin)
	{
		pl = plugin;
	}

	@EventHandler
	public void InventoryClick(InventoryClickEvent e)
	{
		if (e.getWhoClicked() instanceof Player)
		{
			Player p = (Player) e.getWhoClicked();
			if (/* SimpleHub.inHub.contains(p.getUniqueId().toString() */true)
			{
				e.setCancelled(true);
				p.updateInventory();
				updateInventory(p);
				InventoryType type = e.getInventory().getType();
				if (type == InventoryType.PLAYER)
				{
					e.setCancelled(true);
					p.updateInventory();
					updateInventory(p);
				}
				else if (type == InventoryType.CHEST)
				{
					e.setCancelled(true);
					p.updateInventory();
					updateInventory(p);
					ItemStack is = e.getCurrentItem();
					ItemMeta meta = is.getItemMeta();
					String name = meta.getDisplayName();
					for (Game g : SimpleHub.games)
					{
						ItemStack is1 = g.getIs();
						if (Parser.colorparse(
								is1.getItemMeta().getDisplayName())
								.equalsIgnoreCase(name)) ;
						{

							ByteArrayDataOutput out = ByteStreams
									.newDataOutput();
							out.writeUTF("Connect");
							out.writeUTF(g.getCommand());
							p.sendPluginMessage(pl, "BungeeCord",
									out.toByteArray());

							return;
						}
					}
				}
			}
		}
	}

	private void updateInventory(Player p)
	{
		PlayerInventory inv = p.getInventory();

		ItemMeta metaGames = new ItemStack(Material.COMPASS).getItemMeta();
		metaGames.setDisplayName(Parser.colorparse(SimpleHub.prefix
				+ " &8Game Selector"));

		ItemStack games = new ItemStack(Material.COMPASS);
		games.setItemMeta(metaGames);

		ItemMeta metaGun = new ItemStack(Material.DIAMOND_BARDING)
				.getItemMeta();
		metaGun.setDisplayName(Parser.colorparse(SimpleHub.prefix
				+ " &8Fun Gun"));

		ItemStack gun = new ItemStack(Material.DIAMOND_BARDING);
		gun.setItemMeta(metaGun);

		ItemMeta metaSword = new ItemStack(Material.DIAMOND_BARDING)
				.getItemMeta();
		metaSword.setDisplayName(Parser.colorparse(SimpleHub.prefix
				+ " &8PvP Sword"));

		ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
		sword.setItemMeta(metaSword);

		ItemMeta metaClock = new ItemStack(Material.WATCH).getItemMeta();
		if (hasHidden.contains(p.getName()))
		{
			metaClock.setDisplayName("§9Magic Clock> §8Players Hidden");
		}
		else
		{
			metaClock.setDisplayName("§9Magic Clock> §8Players Visible");
		}

		ItemStack clock = new ItemStack(Material.WATCH);
		clock.setItemMeta(metaClock);

		inv.setItem(0, games);
		inv.setItem(1, gun);
		inv.setItem(2, new ItemStack(Material.AIR));
		inv.setItem(3, new ItemStack(Material.AIR));
		// inv.setItem(4, sword);
		inv.setItem(4, new ItemStack(Material.AIR));
		inv.setItem(5, new ItemStack(Material.AIR));
		inv.setItem(6, new ItemStack(Material.AIR));
		inv.setItem(7, new ItemStack(Material.AIR));
		inv.setItem(8, clock);

		p.updateInventory();
	}

	@EventHandler
	public void ItemDrop(PlayerDropItemEvent e)
	{
		Player p = e.getPlayer();
		PlayerInventory i = p.getInventory();
		ItemStack[] c = i.getContents();
		if (/* SimpleHub.inHub.contains(p.getUniqueId().toString() */true)
		{
			e.setCancelled(true);
			p.getInventory().setContents(c);
			updateInventory(p);
		}
	}

	@EventHandler
	public void BlockBreak(BlockBreakEvent e)
	{
		Player p = e.getPlayer();
		if (/* SimpleHub.inHub.contains(p.getUniqueId().toString() */true)
		{
			e.setCancelled(true);
			updateInventory(p);
		}
	}

	@EventHandler
	public void BlockPlace(BlockPlaceEvent e)
	{
		Player p = e.getPlayer();
		if (/* SimpleHub.inHub.contains(p.getUniqueId().toString() */true)
		{
			e.setCancelled(true);
			updateInventory(p);
		}
	}

	@EventHandler
	public void FoodChange(FoodLevelChangeEvent e)
	{
		if (e.getEntity() instanceof Player)
		{
			Player p = (Player) e.getEntity();
			if (/* SimpleHub.inHub.contains(p.getUniqueId().toString() */true)
			{
				e.setCancelled(true);
				p.updateInventory();
				updateInventory(p);
			}
		}
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e)
	{
		Player p = e.getPlayer();
		if (p.getGameMode() != GameMode.CREATIVE
				&& p.getLocation().subtract(0, 1, 0).getBlock().getType() != Material.AIR
				&& !p.isFlying())
		{
			p.setAllowFlight(true);
		}
	}

	@EventHandler
	public void onPlayerToggleFlight(PlayerToggleFlightEvent e)
	{
		Player p = e.getPlayer();
		if (p.getGameMode() == GameMode.CREATIVE) return;
		e.setCancelled(true);
		p.setAllowFlight(false);
		p.setFlying(false);
		p.setVelocity(p.getLocation().getDirection().multiply(1.5).setY(1));
		p.playSound(p.getLocation(), Sound.CAT_MEOW, 10, 0);

	}

	@EventHandler
	public void EntityBlockDamage(EntityDamageEvent e)
	{
		if (e.getEntity() instanceof Player)
		{
			Player p = (Player) e.getEntity();
			if (/* SimpleHub.inHub.contains(p.getUniqueId().toString() */true)
			{
				if (e.getCause() == DamageCause.FALL)
				{
					e.setCancelled(true);
				}
			}
		}
	}

	List<String> hasHidden = new ArrayList<String>();

	@EventHandler
	public void PlayerInteract(PlayerInteractEvent e)
	{
		Player p = e.getPlayer();
		if (/* SimpleHub.inHub.contains(p.getUniqueId().toString() */true
				&& p.getItemInHand().getType() == Material.DIAMOND_BARDING
				&& (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK))
		{
			p.launchProjectile(Snowball.class);
			ItemMeta meta = p.getItemInHand().getItemMeta();
			meta.setDisplayName(Parser.colorparse(SimpleHub.prefix
					+ " &8Fun Gun"));
			p.getItemInHand().setItemMeta(meta);
		}

		if (/* SimpleHub.inHub.contains(p.getUniqueId().toString() */true
				&& p.getItemInHand().getType() == Material.WATCH
				&& (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK))
		{
			if (hasHidden.contains(e.getPlayer().getName()))
			{
				hasHidden.remove(e.getPlayer().getName());
				ItemMeta meta = p.getItemInHand().getItemMeta();
				meta.setDisplayName(Parser
						.colorparse("&9Magic Clock> &8Players Visible"));
				p.getItemInHand().setItemMeta(meta);
				p.sendMessage(Parser.colorparse(SimpleHub.prefix
						+ " &8Players Visible"));
				for (Player p1 : Bukkit.getServer().getOnlinePlayers())
				{
					if (p1 != e.getPlayer())
					{
						e.getPlayer().showPlayer(p1);
					}
				}
			}

			else
			{
				ItemMeta meta = p.getItemInHand().getItemMeta();
				meta.setDisplayName(Parser
						.colorparse("&9Magic Clock> &8Players Hidden"));
				p.getItemInHand().setItemMeta(meta);
				p.sendMessage(Parser.colorparse(SimpleHub.prefix
						+ " Players Hidden"));
				hasHidden.add(e.getPlayer().getName());
				for (Player p1 : Bukkit.getServer().getOnlinePlayers())
				{
					if (p1 != e.getPlayer())
					{
						e.getPlayer().hidePlayer(p1);
					}
				}
			}
		}
		else if (/* SimpleHub.inHub.contains(p.getUniqueId().toString() */true
				&& p.getItemInHand().getType() == Material.COMPASS
				&& (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK))
		{

			ItemMeta meta = p.getItemInHand().getItemMeta();
			meta.setDisplayName(Parser.colorparse(SimpleHub.prefix
					+ " &8Game Selector"));
			p.getItemInHand().setItemMeta(meta);
			int size1 = SimpleHub.games.size();
			int size = 9;
			if (size1 < 1)
			{
				size = 9;
			}
			else if (size1 > 54)
			{
				size = 54;
			}
			else
			{
				size1 += 8;
				size = size1 - (size1 % 9);
			}

			Inventory inv = Bukkit.createInventory(p, size,
					Parser.colorparse("&9&lGames"));
			for (Game g : SimpleHub.games)
			{
				inv.addItem(g.getIs());
			}
			p.openInventory(inv);
			return;
		}
	}

	@EventHandler
	public void ProjectileHit(ProjectileHitEvent e)
	{
		Projectile p = e.getEntity();
		Location loc = p.getLocation();
		if (e.getEntityType() == EntityType.SNOWBALL)
		{
			p.playEffect(EntityEffect.WOLF_HEARTS);
			loc.getWorld().playSound(loc, Sound.CAT_MEOW, 10, 0);
			loc.getWorld().playEffect(loc, Effect.POTION_BREAK, 0, 1000);

		}
	}

	@EventHandler
	public void PlayerJoin(PlayerJoinEvent e)
	{
		e.setJoinMessage(Parser.colorparse("&8[&2+&8] "
				+ e.getPlayer().getName()));
		for (Player p : Bukkit.getServer().getOnlinePlayers())
		{
			if (p != e.getPlayer())
			{
				if (hasHidden.contains(p.getName()))
				{
					p.hidePlayer(e.getPlayer());
				}

				else
				{
					p.showPlayer(e.getPlayer());
				}
			}
		}
		updateInventory(e.getPlayer());
	}

	@EventHandler
	public void PlayerLeave(PlayerQuitEvent e)
	{
		e.setQuitMessage(Parser.colorparse("&8[&4-&8] "
				+ e.getPlayer().getName()));
	}

	public static List<String> inpvp = new ArrayList<String>();

	@EventHandler
	public void PlayerItemHeld(PlayerItemHeldEvent e)
	{
		Player p = e.getPlayer();
		if (SimpleHub.inHub.contains(p.getName()))
		{
			if (p.getInventory().getItem(e.getNewSlot()).getType() == Material.DIAMOND_SWORD
					|| p.getItemInHand().getType() == Material.DIAMOND_SWORD)
			{
				Bukkit.getServer().getPluginManager()
						.callEvent(new LobbyPvPStatusChangeEvent(p, true));
			}
			else if (p.getInventory().getItem(e.getPreviousSlot()).getType() == Material.DIAMOND_SWORD)
			{
				Bukkit.getServer().getPluginManager()
						.callEvent(new LobbyPvPStatusChangeEvent(p, false));
			}
		}
	}

	@EventHandler
	public void EntityDamageByEntity(EntityDamageByEntityEvent e)
	{
		if (e.getEntity().getType() == EntityType.PLAYER
				&& e.getDamager().getType() == EntityType.PLAYER)
		{
			Player p = (Player) e.getEntity();
			Player d = (Player) e.getDamager();

			if (inpvp.contains(p.getName()))
			{
				System.out.println("The Player Is In PvP Mode");
			}
			else
			{
				System.out.println("The Player Is Not In PvP Mode");
			}
			if (!inpvp.contains(d))
			{
				d.sendMessage(Parser.colorparse(SimpleHub.prefix
						+ " &4You Are Not In Lobby PvP Mode"));
				e.setCancelled(true);
			}
			else if (inpvp.contains(d) && !inpvp.contains(p))
			{
				d.sendMessage(Parser.colorparse(SimpleHub.prefix + " &8"
						+ p.getDisplayName() + "&4Is Not In Lobby PvP Mode"));
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void LobbyPvPStatusChange(LobbyPvPStatusChangeEvent e)
	{
		Player p = e.p;
		if (e.change)
		{
			p.getInventory().setArmorContents(SimpleHub.pvpArmor);
			p.sendMessage(Parser.colorparse(SimpleHub.prefix
					+ " &8You Activated Lobby PvP"));
			inpvp.add(p.getName());
		}
		else if (!e.change)
		{
			p.getInventory().setArmorContents(new ItemStack[4]);
			p.sendMessage(Parser.colorparse(SimpleHub.prefix
					+ " &8You De-Activated Lobby PvP"));
			if (inpvp.contains(p.getName()))
			{
				inpvp.remove(p.getName());
			}
		}
	}

}
