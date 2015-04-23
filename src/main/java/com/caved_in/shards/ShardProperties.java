package com.caved_in.shards;

import com.caved_in.commons.effect.ParticleEffects;
import com.caved_in.commons.utilities.ArrayUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import java.util.HashMap;
import java.util.Map;

public class ShardProperties {
	public static Material SHARD_MATERIAL = Material.GOLD_NUGGET;

	public static boolean SHARDS_ON_FIRE = false;

	/* Used to select a location in the radius from where shards drop from a shard bomb*/
	public static double SHARD_BOMB_VARIATION = 15.5;
	
	public static double SHARD_BOMB_PLAYER_VARIATION = 1.0;

	public static int SHARDS_BOMB_MIN_WORTH = 3;
	public static int SHARDS_BOMB_MAX_WORTH = 7;
	
	public static int SHARDS_BOMB_PLAYER_MIN_WORTH = 1;
	
	public static int SHARDS_BOMB_PLAYER_MAX_WORTH = 3;

	/* The minimum amount of shards to spawn per power-level of the bomb. */
	public static int SHARD_BOMB_MIN = 5;

	/* The maxiumum amount of shards to spawn per power-level of the bomb */
	public static int SHARD_BOMB_MAX = 10;
	
	public static int SHARD_BOMB_PLAYER_MIN = 2;
	
	public static int SHARD_BOMB_PLAYER_MAX = 3;

	/* The minimum amount of currency awarded by the shard. */
	public static int MIN_SHARD_AWARD = 5;

	/* The maximum amount of currency awarded by the shard. */
	public static int MAX_SHARD_AWARD = 25;

	/* Out of 100, the chances of a shard dropping when you kill a player */
	public static int PLAYER_KILLED_PLAYER_CHANCE = 8;

	/* Whether or not to drop when a player was killed. */
	public static boolean DROP_ON_PLAYER_DEATH = true;

	/* Each entity, and their chances of dropping a shard when killed */
	private static final Map<EntityType, Integer> entityDropChances = new HashMap<>();

	static {
		//Creepers have a 10% chance to drop a shard.
		setMobDropChance(EntityType.CREEPER,5);

		//Skeletons have a 5% chance to drop a shard
		setMobDropChance(EntityType.SKELETON,4);

		//Blazes have a 8% chance to drop a shard.
		setMobDropChance(EntityType.BLAZE, 4);

		//Enderman have a 9% chance to drop a shard.
		setMobDropChance(EntityType.ENDERMAN,5);

		//Ghasts have a 11% chance of dropping a shard.
		setMobDropChance(EntityType.GHAST,11);

		//Giants have a 100% chance of dropping a shard.
		setMobDropChance(EntityType.GIANT,100);
	}

	public static void setMobDropChance(EntityType type, int amount) {
		entityDropChances.put(type, amount);
	}

	/**
	 * Out of 100, the likely-hood that the given entitytype will drop a shard when killed.
	 * @param type type of entity to get the drop rate for.
	 * @return the chance a mob has to drop a shard.
	 */
	public static int getMobDropChance(EntityType type) {
		if (!entityDropChances.containsKey(type)) {
			return 0;
		}

		return entityDropChances.get(type);
	}

	public static void setAwardRange(int min, int max) {
		MIN_SHARD_AWARD = min;
		MAX_SHARD_AWARD = max;
	}
	
	private static final ParticleEffects[] SPAWN_EFFECTS = {ParticleEffects.EXPLOSION_NORMAL,ParticleEffects.CRIT,ParticleEffects.HEART,ParticleEffects.BUBBLE,ParticleEffects.NOTE};
	public static ParticleEffects getRandomSpawnEffect() {
		return ArrayUtils.getRandom(SPAWN_EFFECTS);
		
	}
}
