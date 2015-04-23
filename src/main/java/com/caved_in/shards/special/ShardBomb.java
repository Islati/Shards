package com.caved_in.shards.special;

import com.caved_in.commons.effect.ParticleEffects;
import com.caved_in.commons.fireworks.Fireworks;
import com.caved_in.commons.location.Locations;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.sound.Sounds;
import com.caved_in.commons.utilities.NumberUtil;
import com.caved_in.shards.ShardProperties;
import com.caved_in.shards.Shards;
import com.caved_in.shards.special.effects.BombEffect;
import org.bukkit.Location;
import org.bukkit.Sound;

import java.util.ArrayList;
import java.util.List;

public class ShardBomb {
    private static Shards shards = Shards.getInstance();
    
    /*
    Whether or not it's to spawn near players on the server, as opposed to a specific location
     */
    private boolean nearPlayers = false;

    /* Whether or not the bomb has started exploding */
    private boolean started = false;
    
    /*
    How long the bomb should explode for, duration of its effects
     */
    private int duration = 25;
    
    /* Fuse! The higher the number, the longer before exploding! */
    private int delay = 5;
    
    /* Time the time started displaying */
    private long timeStamp;

    /* Time the bomb finished displaying */
    private long finishTime;

    /*
    The power of the bomb that's going off! :)
    */
    private int power = 6;

    /*
    If it's not being spawned around players on the server, then a location
    needs to be passed to it!
     */
    private Location loc;
    
    /*
    Effects to player after launching the shard bomb!
     */
    private List<BombEffect> bombEffects = new ArrayList<>();

    public ShardBomb(Location loc) {
        this.loc = loc;
    }

    public ShardBomb power(int power) {
        this.power = power;
        return this;
    }

    public ShardBomb location(Location loc) {
        this.loc = loc;
        return this;
    }
    
    public ShardBomb delay(int seconds) {
        this.delay = seconds;
        return this;
    }

    public ShardBomb duration(int seconds) {
        duration = seconds;
        return this;
    }

    public ShardBomb nearPlayers(boolean nearPlayers) {
        this.nearPlayers = nearPlayers;
        return this;
    }
    
    public ShardBomb addEffect(BombEffect effect) {
        bombEffects.add(effect);
        return this;
    }

    public void explode() {
        Fireworks.playFirework(Locations.getRandomLocation(loc, ShardProperties.SHARD_BOMB_VARIATION), Fireworks.randomFireworkEffect());

        
        
        if (nearPlayers) {
            for (int i = 0; i < power; i++) {

                //todo Bomb everyones location, not just give them the shit

                Players.stream().forEach(p -> {
                    int shardsAmount = NumberUtil.getRandomInRange(ShardProperties.SHARD_BOMB_PLAYER_MIN, ShardProperties.SHARD_BOMB_PLAYER_MAX);
                    for (int j = 0; j < shardsAmount; j++) {
                        Shards.spawn(p.getLocation(), NumberUtil.getRandomInRange(ShardProperties.SHARDS_BOMB_PLAYER_MIN_WORTH, ShardProperties.SHARDS_BOMB_PLAYER_MAX_WORTH));
                        ParticleEffects.sendToLocation(ShardProperties.getRandomSpawnEffect(), p.getLocation(), 3);
                    }
                });
            }
            
            return;
        }


        for (int i = 0; i < power; i++) {
            int shardAmount = NumberUtil.getRandomInRange(ShardProperties.SHARD_BOMB_MIN, ShardProperties.SHARD_BOMB_MAX);
            for (int j = 0; j < shardAmount; j++) {
                Location shardSpawnLoc = Locations.getRandomLocation(loc, ShardProperties.SHARD_BOMB_VARIATION);
                Shards.spawn(shardSpawnLoc, NumberUtil.getRandomInRange(ShardProperties.SHARDS_BOMB_MIN_WORTH, ShardProperties.SHARDS_BOMB_MAX_WORTH));
                ParticleEffects.sendToLocation(ShardProperties.getRandomSpawnEffect(), shardSpawnLoc, 3);
            }

            Sounds.playSoundForPlayersAtLocation(loc, Sound.FIREWORK_BLAST, 0.5f, 0.8f);
        }

        Fireworks.playFirework(Locations.getRandomLocation(loc, ShardProperties.SHARD_BOMB_VARIATION), Fireworks.randomFireworkEffect());
    }

    public int power() {
        return power;
    }

    public int delay() {
        return delay;
    }

    public int duration() {
        return duration;
    }

    public boolean isNearPlayers() {
        return nearPlayers;
    }

    public Location getLoc() {
        return loc;
    }
}
