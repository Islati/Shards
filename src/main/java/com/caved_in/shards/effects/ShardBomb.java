package com.caved_in.shards.effects;

import com.caved_in.commons.effect.ParticleEffects;
import com.caved_in.commons.fireworks.Fireworks;
import com.caved_in.commons.location.Locations;
import com.caved_in.commons.sound.Sounds;
import com.caved_in.commons.time.TimeHandler;
import com.caved_in.commons.time.TimeType;
import com.caved_in.commons.utilities.NumberUtil;
import com.caved_in.shards.ShardProperties;
import com.caved_in.shards.Shards;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

public class ShardBomb {
    private static Shards shards = Shards.getInstance();

    private int power = 6;
    private Location loc;

    private int duration = 25;

    private boolean started = false;

    private long timeStamp;

    private long finishTime;

    public ShardBomb(Location loc) {
        this.loc = loc;
    }

    public ShardBomb power(int power) {
        this.power = power;
        return this;
    }

    public int power() {
        return power;
    }

    public ShardBomb location(Location loc) {
        this.loc = loc;
        return this;
    }

    public ShardBomb duration(int seconds) {
        duration = seconds;
        return this;
    }

    public void explode() {
        Fireworks.playFirework(Locations.getRandomLocation(loc,ShardProperties.SHARD_BOMB_VARIATION), Fireworks.randomFireworkEffect());

        for(int i = 0; i < power;i++) {
            int shardAmount = NumberUtil.getRandomInRange(ShardProperties.SHARD_BOMB_MIN,ShardProperties.SHARD_BOMB_MAX);
            for(int j = 0; j < shardAmount; j++) {
                Location shardSpawnLoc = Locations.getRandomLocation(loc, ShardProperties.SHARD_BOMB_VARIATION);
                Shards.spawn(shardSpawnLoc,NumberUtil.getRandomInRange(ShardProperties.SHARDS_BOMB_MIN_WORTH,ShardProperties.SHARDS_BOMB_MAX_WORTH));
                ParticleEffects.sendToLocation(ParticleEffects.EXPLODE,shardSpawnLoc,3);
            }

            Sounds.playSoundForPlayersAtLocation(loc, Sound.FIREWORK_BLAST,0.5f,0.8f);
        }

        Fireworks.playFirework(Locations.getRandomLocation(loc,ShardProperties.SHARD_BOMB_VARIATION), Fireworks.randomFireworkEffect());
    }

    public long duration() {
        return duration;
    }
}
