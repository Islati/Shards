package com.caved_in.shards.special.effects;

import com.caved_in.commons.utilities.NumberUtil;
import org.bukkit.entity.Item;

public class FireworkBombEffect implements BombEffect {
    
    private static FireworkBombEffect instance = null;
    
    private int FIREWORK_EXPLOSION_CHANCE = 10;
    
    public FireworkBombEffect() {
        
    }
    
    public FireworkBombEffect chance(int chance) {
        FIREWORK_EXPLOSION_CHANCE = chance;
        return this;
    }
    
    @Override
    public boolean perform(Item shard) {
        if (!NumberUtil.percentCheck(FIREWORK_EXPLOSION_CHANCE)) {
            
        }
        return false;
    }
}
