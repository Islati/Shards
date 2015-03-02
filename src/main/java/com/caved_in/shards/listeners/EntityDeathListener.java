package com.caved_in.shards.listeners;

import com.caved_in.commons.utilities.NumberUtil;
import com.caved_in.shards.ShardProperties;
import com.caved_in.shards.Shards;
import com.caved_in.shards.event.ShardSpawnEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityDeathListener implements Listener {

	@EventHandler
	public void onEntityDeathListener(EntityDeathEvent e) {
		int dropChance = ShardProperties.getMobDropChance(e.getEntityType());

		if (!NumberUtil.percentCheck(dropChance)) {
			return;
		}

		ShardSpawnEvent.call(e.getEntity().getLocation());
	}
}
