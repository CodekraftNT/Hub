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
/**
 * @author codekrafter
 *
 */
package net.codekrafter.plugins.simplehub.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;


/**
 * @author codekrafter
 *
 */
public class LobbyPvPStatusChangeEvent extends Event
{

	/**
	 * @author codekrafter
	 *
	 * @param change
	 * @param p
	 */
	public LobbyPvPStatusChangeEvent(Player p, boolean change)
	{
		this.change = change;
		this.p = p;
	}

	static HandlerList handlers = new HandlerList();
	boolean change;
	Player p;
	
	/* (non-Javadoc)
	 * @see org.bukkit.event.Event#getHandlers()
	 */
	@Override
	public HandlerList getHandlers()
	{
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
	    return handlers;
	}

}
