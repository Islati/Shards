package com.caved_in.shards.special;

import com.caved_in.commons.effect.ParticleEffects;
import com.caved_in.commons.utilities.NumberUtil;
import com.caved_in.shards.Shards;
import com.caved_in.shards.util.RandomUtils;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class ShardFountain extends BukkitRunnable {
    /**
     * Particle of the fountain
     */
    public ParticleEffects particle = ParticleEffects.HEART;

    /**
     * Amount of strands (10)
     */
    public int strands = 10;

    /**
     * Particles per iteration per strand (100)
     */
    public int particlesStrand = 150;

    /**
     * Particles per iteration in the spout
     */
    public int particlesSpout = 200;

    /**
     * Radius of strands in blocks
     */
    public float radius = 5;

    /**
     * Radius of spout as a fraction of the fountain (0.1)
     */
    public float radiusSpout = .1f;

    /**
     * Height of the fountain
     */
    public float height = 3;

    /**
     * Height of the spout in blocks
     */
    public float heightSpout = 7;

    /**
     * Rotation of the fountain on the Y-Axis (Fraction of PI)
     */
    public double rotation = Math.PI / 4;

    /**
     * The delay before repeating the task; used if it's an effect that must repeat!
     */
    public int period = 2;
    
    public int iterations = 100;
    
    public Location location;

    public ShardFountain() {
        
    }
    
    public ShardFountain particlesPerStrand(int amount) {
        this.particlesStrand = amount;
        return this;
    }
    
    public ShardFountain particlesPerSpout(int amount) {
        this.particlesSpout = amount;
        return this;
    }
    
    public ShardFountain strands(int strands) {
        this.strands = strands;
        return this;        
    }
    
    public ShardFountain spoutRadius(float radius) {
        this.radiusSpout = radius;
        return this;
    }
    
    public ShardFountain height(int blockHeight) {
        this.height = blockHeight;
        return this;
    }
    
    public ShardFountain period(int period) {
        this.period = period;
        return this;
    }
    
    public ShardFountain rotation(int fraction) {
        this.rotation = Math.PI / fraction;
        return this;
    }
    
    public ShardFountain particle(ParticleEffects effect) {
        this.particle = effect;
        return this;
    }
    
    public ShardFountain iterations(int iters) {
        this.iterations = iters;
        return this;
    }
    
    public ShardFountain location(Location loc) {
        this.location = loc;
        return this;
    }

    @Override
    public void run() {
        iterations--;
        
        if (iterations <= 0) {
            cancel();
            return;
        }
        
        for (int i = 1; i <= strands; i++) {
            double angle = 2 * i * Math.PI / strands + rotation;
            for (int j = 1; j <= particlesStrand; j++) {
                float ratio = (float) j / particlesStrand;
                double x, y, z;
                x = Math.cos(angle) * radius * ratio;
                y = Math.sin(Math.PI * j / particlesStrand) * height;
                z = Math.sin(angle) * radius * ratio;
                location.add(x, y, z);
                
                ParticleEffects.sendToLocation(particle,location,1);
                
                if (NumberUtil.percentCheck(5)) {
                    Shards.chanceSpawn(location, 1);
                }

                //display(particle, location);
                location.subtract(x, y, z);
            }
        }
        
        for (int i = 0; i < particlesSpout; i++) {
            Vector v = RandomUtils.getRandomCircleVector().multiply(RandomUtils.random.nextFloat() * radius * radiusSpout);
            v.setY(RandomUtils.random.nextFloat() * heightSpout);
            location.add(v);

            ParticleEffects.sendToLocation(particle,location,1);

            location.subtract(v);
        }
    }
}
