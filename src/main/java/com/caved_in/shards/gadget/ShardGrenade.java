package com.caved_in.shards.gadget;

import com.caved_in.commons.game.item.ThrowableItem;
import com.caved_in.commons.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ShardGrenade extends ThrowableItem {
    public ShardGrenade() {
        super(ItemBuilder.of(Material.GOLDEN_APPLE).name("&6&lShard Grenade").lore("&eThe holy hand grenade of goodies!","&c&lShare the wealth!!").item(), 5);
    }

    @Override
    public void handle(Player player, Item item) {

    }

    @Override
    public int id() {
        return 133337;
    }
}
