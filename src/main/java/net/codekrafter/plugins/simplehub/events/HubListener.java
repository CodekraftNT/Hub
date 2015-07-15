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
import java.util.HashMap;
import java.util.List;

import net.codekrafter.plugins.Game;
import net.codekrafter.plugins.simplehub.SimpleHub;
import net.codekrafter.plugins.utils.Parser;

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
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class HubListener implements Listener
{

	@EventHandler
	public void InventoryClick(InventoryClickEvent e)
	{
		if (e.getWhoClicked() instanceof Player)
		{
			Player p = (Player) e.getWhoClicked();
			if (SimpleHub.inHub.contains(p.getUniqueId().toString()))
			{
				e.setCancelled(true);
				p.updateInventory();
				InventoryType type = e.getInventory().getType();
				if (type == InventoryType.PLAYER)
				{
					e.setCancelled(true);
					p.updateInventory();
				}
				else if (type == InventoryType.CHEST)
				{
					e.setCancelled(true);
					p.updateInventory();
					ItemStack is = e.getCurrentItem();
					ItemMeta meta = is.getItemMeta();
					Bukkit.getLogger().info(is.getType().name());
					String name = meta.getDisplayName();
					for (Game g : SimpleHub.games)
					{
						ItemStack is1 = g.getIs();
						if (Parser.colorparse(is1.getItemMeta()
								.getDisplayName()).equalsIgnoreCase(name));
						{
							Bukkit.getServer().dispatchCommand(
									Bukkit.getServer().getConsoleSender(),
									Parser.commandparse(g.getCommand(), p));
							return;
						}
					}
				}
			}
		}
	}

	private void updateInventory(Player p)
	{
		PlayerInventory pinv = p.getInventory();
		pinv.setContents(SimpleHub.hubInv.getContents());

	}

	@EventHandler
	public void ItemDrop(PlayerDropItemEvent e)
	{
		Player p = e.getPlayer();
		PlayerInventory i = p.getInventory();
		ItemStack[] c = i.getContents();
		if (SimpleHub.inHub.contains(p.getUniqueId().toString()))
		{
			e.setCancelled(true);
			p.getInventory().setContents(c);
		}
	}

	@EventHandler
	public void BlockBreak(BlockBreakEvent e)
	{
		Player p = e.getPlayer();
		if (SimpleHub.inHub.contains(p.getUniqueId().toString()))
		{
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void BlockPlace(BlockPlaceEvent e)
	{
		Player p = e.getPlayer();
		if (SimpleHub.inHub.contains(p.getUniqueId().toString()))
		{
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void FoodChange(FoodLevelChangeEvent e)
	{
		if (e.getEntity() instanceof Player)
		{
			Player p = (Player) e.getEntity();
			if (SimpleHub.inHub.contains(p.getUniqueId().toString()))
			{
				e.setCancelled(true);
				p.updateInventory();
			}
		}
	}

	HashMap<String, Boolean> cooldown = new HashMap<String, Boolean>();
	List<String> fixed = new ArrayList<String>();

	@SuppressWarnings("deprecation")
	@EventHandler
	public void PlayerMove(PlayerMoveEvent e)
	{
		Player p = e.getPlayer();

		if (p.getAllowFlight() == true && !fixed.contains(p))
		{
			p.setFlying(false);
			p.setAllowFlight(false);
			fixed.add(p.getUniqueId().toString());
		}

		if (p.getGameMode() == GameMode.CREATIVE) return;

		if (cooldown.get(p) != null && cooldown.get(p) == true)
		{
			p.setAllowFlight(true);
		}
		else
		{
			p.setAllowFlight(false);
		}

		Location loc = p.getLocation();
		loc.setY(p.getLocation().getY() - 1);
		if (loc.getBlock().getType() != Material.AIR)
		{
			cooldown.put(p.getUniqueId().toString(), true);
		}

		if (cooldown.get(p) != null && cooldown.get(p) == false)
		{
			for (Player pl : Bukkit.getOnlinePlayers())
			{
				pl.playEffect(p.getLocation(), Effect.MOBSPAWNER_FLAMES, 2004);
			}
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onFly(PlayerToggleFlightEvent e)
	{
		Player p = e.getPlayer();

		if (p.getGameMode() == GameMode.CREATIVE) return;
		if (cooldown.get(p) == true)
		{
			e.setCancelled(true);
			cooldown.put(p.getUniqueId().toString(), false);
			p.setVelocity(p.getLocation().getDirection().multiply(1.6D)
					.setY(1.0D));

			for (Player pl : Bukkit.getOnlinePlayers())
			{
				pl.playEffect(p.getLocation(), Effect.POTION_BREAK, 16451);
				pl.playSound(pl.getLocation(), Sound.GHAST_FIREBALL, 10, 10);
			}

			p.setAllowFlight(false);
		}
	}

	@EventHandler
	public void EntityBlockDamage(EntityDamageEvent e)
	{
		if (e.getEntity() instanceof Player)
		{
			Player p = (Player) e.getEntity();
			if (SimpleHub.inHub.contains(p.getUniqueId().toString()))
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
		if (SimpleHub.inHub.contains(p.getUniqueId().toString())
				&& p.getItemInHand().getType() == Material.DIAMOND_BARDING
				&& (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK))
		{
			p.launchProjectile(Snowball.class);
			ItemMeta meta = p.getItemInHand().getItemMeta();
			meta.setDisplayName(Parser.colorparse(SimpleHub.prefix
					+ " &8Fun Gun"));
			p.getItemInHand().setItemMeta(meta);
		}

		if (SimpleHub.inHub.contains(p.getUniqueId().toString())
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
		else if (SimpleHub.inHub.contains(p.getUniqueId().toString())
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
					Parser.colorparse("               &9&lGames"));
			for (Game g : SimpleHub.games)
			{
				inv.addItem(g.getIs());
			}
			InventoryView invv = p.openInventory(inv);
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
	}

	@EventHandler
	public void PlayerLeave(PlayerQuitEvent e)
	{
		e.setQuitMessage(Parser.colorparse("&8[&4-&8] "
				+ e.getPlayer().getName()));
	}

}
