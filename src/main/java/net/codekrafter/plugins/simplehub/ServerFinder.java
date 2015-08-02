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

package net.codekrafter.plugins.simplehub;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;

/**
 * @author codekrafter
 *
 */
public class ServerFinder
{

	static public ServerInfo getServer(String startsWith)
	{
		Map<String, ServerInfo> servers = ProxyServer.getInstance()
				.getServers();
		Map<ServerInfo, Integer> serversPlayers = new HashMap<ServerInfo, Integer>();
		Entry<ServerInfo, Integer> selectedServer = null;
		for (Entry<String, ServerInfo> entry : servers.entrySet())
		{
			if (entry.getKey().startsWith(startsWith))
			{
				serversPlayers.put(entry.getValue(), entry.getValue()
						.getPlayers().size());
			}
		}

		for (Entry<ServerInfo, Integer> entry : serversPlayers.entrySet())
		{
			if (selectedServer == null)
			{
				selectedServer = entry;
			}
			else
			{
				if (entry.getValue() < selectedServer.getValue())
				{
					selectedServer = entry;
				}
			}
		}
		return selectedServer.getKey();
	}
}
