package com.caved_in.shards;

import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Title;
import com.caved_in.commons.item.ItemBuilder;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.plugin.BukkitPlugin;
import com.caved_in.commons.utilities.NumberUtil;
import com.caved_in.commons.world.Worlds;
import com.caved_in.shards.commands.ShardsCommand;
import com.caved_in.shards.effects.ShardBomb;
import com.caved_in.shards.listeners.EntityDeathListener;
import com.caved_in.shards.listeners.ItemPickupListener;
import com.caved_in.shards.listeners.PlayerDeathListener;
import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Shards extends BukkitPlugin {
	private static final String META_IDENTIFIER = "TUNNELS_SHARD";
	private static Shards instance;

	private static Title shardBombTitle = new Title("&cShards Bomb Incoming","&aReady everyone!",1,2,1);


	public static Shards getInstance() {
		return instance;
	}

	@Override
	public void startup() {
		instance = this;

		registerListeners(
				new PlayerDeathListener(),
				new EntityDeathListener(),
				new ItemPickupListener()
		);

		registerCommands(new ShardsCommand());

	}

	@Override
	public void shutdown() {

	}
	
	@Override
	public String getAuthor() {
		return "Brandon Curtis";
	}

	@Override
	public void initConfig() {

	}

	/**
	 * Spawn the item with a chance of succeeding.
	 * @param loc location to spawn the shard at.
	 * @param chance chance th
	 * @return shard that was spawned.
	 */
	public static Item chanceSpawn(Location loc, int chance) {
		if (!NumberUtil.percentCheck(chance)) {
			return null;
		}

		return spawn(loc);
	}
	
	public static Set<Item> chanceSpawn(Location loc, int chance, int amount) {
		Set<Item> spawnedShards = new HashSet<>();
		
		for(int i = 0; i < amount; i++) {
			Item shard = chanceSpawn(loc,chance);
			
			if (shard == null) {
				continue;
			}
			spawnedShards.add(shard);
		}
		
		return spawnedShards;
		
	}

	/**
	 * Spawn a shard at the given location with a worth within the bounds defined in ShardProperties
	 * @param loc location to spawn the shard at.
	 * @return shard that was spawned
	 */
	public static Item spawn(Location loc) {
		int amount = NumberUtil.getRandomInRange(ShardProperties.MIN_SHARD_AWARD,ShardProperties.MAX_SHARD_AWARD);
		return spawn(loc,amount);
	}

	/**
	 * Spawn a shard at the given location with the defined worth.
	 * @param loc location to spawn the shard at.
	 * @param worth amount of currency to award to the player when picking up the shard.
	 * @return shard that was spawned
	 */
	public static Item spawn(Location loc, int worth) {
		Item shard = Worlds.dropItem(loc, ItemBuilder.of(ShardProperties.SHARD_MATERIAL).item());
		//If the shards are to appear on-fire, then do so!
		if (ShardProperties.SHARDS_ON_FIRE) {
			shard.setFireTicks(Integer.MAX_VALUE);
		}

		shard.setMetadata(META_IDENTIFIER, new FixedMetadataValue(Shards.getInstance(), worth));
		return shard;
	}

	public static void bomb(Location loc,int power, int durationSeconds) {
		Shards shards = getInstance();

		ShardBomb bomb = new ShardBomb(loc).duration(durationSeconds).power(power);

		shardBombTitle.broadcast();

		for(int i = 0; i < durationSeconds; i++) {
			shards.getThreadManager().runTaskLater(bomb::explode,100 + (i * 20));
		}
	}

	public static boolean isShard(Item item) {
		return item.hasMetadata(META_IDENTIFIER);
	}

	public static int getWorth(Item item) {
		if (!isShard(item)) {
			return 0;
		}

		return item.getMetadata(META_IDENTIFIER).get(0).asInt();
	}
}
