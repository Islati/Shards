package com.caved_in.shards.listeners;

import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.player.Players;
import com.caved_in.shards.Shards;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import sun.security.provider.SHA;

public class ItemPickupListener implements Listener {

	@EventHandler
	public void onItemPickup(PlayerPickupItemEvent e) {
		Item item = e.getItem();

		//Assure the item we're working with is a shard.
		if (!Shards.isShard(item)) {
			return;
		}

		//If it's a shard, stop the player from picking it up.
		e.setCancelled(true);

		//Get the worth of the shard
		int worth = Shards.getWorth(item);

		int itemCount = item.getItemStack().getAmount();


		Player p = e.getPlayer();

		worth = worth * itemCount;

		Chat.debug(String.format("%s picked up %s shards worth %s total",p.getName(),itemCount,worth));

		//Give the player money equal to the worth of the shard
		Players.giveMoney(p,worth,false);

		//Send them a message saying what they found
		Chat.message(p,String.format("&aYou've found &6%s&a &eshards&a earning you &e%s &axp",itemCount,worth));

		//Remove the item, as we don't need it anymore.
		item.remove();
	}
}
