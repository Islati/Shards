package com.caved_in.shards.listeners;

import com.caved_in.commons.utilities.NumberUtil;
import com.caved_in.shards.ShardProperties;
import com.caved_in.shards.Shards;
import com.caved_in.shards.event.ShardSpawnEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		//If we're not dropping shards when the player dies, then quit.
		if (!ShardProperties.DROP_ON_PLAYER_DEATH) {
			return;
		}

		//Assure that we're able to drop it (it matches the chance %)
		if (!NumberUtil.percentCheck(ShardProperties.PLAYER_KILLED_PLAYER_CHANCE)) {
			return;
		}

		//Create and call a shard-spawn-event where the entity was killed.
		ShardSpawnEvent.call(e.getEntity().getLocation());
	}

}
