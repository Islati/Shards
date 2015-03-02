package com.caved_in.shards.event;

import com.caved_in.commons.plugin.Plugins;
import com.caved_in.commons.utilities.NumberUtil;
import com.caved_in.shards.Shards;
import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;


public class ShardSpawnEvent extends Event implements Cancellable {
	private static final HandlerList handlers = new HandlerList();

	private Location loc;

	private int worth = 0;

	private boolean cancelled = false;

	public static void call(Location loc) {
		ShardSpawnEvent event = new ShardSpawnEvent(loc);
		Plugins.callEvent(event);
		ShardSpawnEvent.handle(event);
	}

	public ShardSpawnEvent(Location loc) {
		this.loc = loc;
	}

	public ShardSpawnEvent(Location loc, int worth) {
		this.loc = loc;
		this.worth = worth;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean b) {
		this.cancelled = b;
	}

	public Location getLocation() {
		return loc;
	}

	public int getWorth() {
		return worth;
	}

	public void setWorth(int amt) {
		this.worth = amt;
	}

	public static void handle(ShardSpawnEvent e) {
		if (e.isCancelled()) {
			return;
		}

		int worth = e.getWorth();

		if (worth <= 0) {
			Shards.spawn(e.getLocation());
		} else {
			Shards.spawn(e.getLocation(),worth);
		}
	}
}
