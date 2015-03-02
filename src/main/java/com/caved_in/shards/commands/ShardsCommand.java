package com.caved_in.shards.commands;

import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.chat.Title;
import com.caved_in.commons.command.Arg;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.command.FlagArg;
import com.caved_in.commons.command.Flags;
import com.caved_in.commons.entity.Entities;
import com.caved_in.commons.menu.HelpScreen;
import com.caved_in.commons.menu.ItemFormat;
import com.caved_in.commons.menu.Menus;
import com.caved_in.commons.menu.PageDisplay;
import com.caved_in.commons.player.Players;
import com.caved_in.shards.ShardProperties;
import com.caved_in.shards.Shards;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import static java.lang.String.format;

public class ShardsCommand {

	@Command(identifier = "shards",permissions = "shards.admin")
	public void onShardsCommand(Player p) {
		Chat.message(p, "&aUse &e/shards help&a for help with this command.");
	}

	@Command(identifier = "shards player-drop")
	public void onShardsPlayerDropCommand(Player p, @Arg(name="chance",verifiers = "min[1]|max[100]")int chance) {
		ShardProperties.PLAYER_KILLED_PLAYER_CHANCE = chance;
		Chat.message(p, format("&aChanged the player drop rate to &e%s&a!",chance));
	}

	@Command(identifier = "shards mob-drop")
	public void onShardsSetMobDropCommand(Player p, @Arg(name="mob type")EntityType type, @Arg(name = "chance",verifiers = "min[1]|max[100]")int chance) {
		ShardProperties.setMobDropChance(type,chance);
		Chat.message(p,format("&aSet the drop chance of &e%s&a to &e%s&a", Entities.getDefaultName(type),chance));
	}

	@Command(identifier = "shards flaming")
	public void onShardsFlame(Player p, @Arg(name="value")boolean flame) {
		ShardProperties.SHARDS_ON_FIRE = flame;
		Chat.message(p, format("&aShards will %s&e be set on fire.",flame ? "&anow" : "&cno longer"));
	}

	@Command(identifier = "shards bomb-radius")
	public void onShardsBombRadiusCommand(Player p, @Arg(name = "radius",description = "Radius in which shards will spawn around at", def="15")double radius) {
		ShardProperties.SHARD_BOMB_VARIATION = radius;

		Chat.message(p,String.format("&aShard bomb radius is now set to &e%s",radius));
	}

	@Command(identifier = "shards bomb")
	@Flags(identifier = "-cursor",description = {"Start the shard bomb above your cursors location"})
	public void onShardsBombCommand(Player p, @Arg(name = "power", def="10") int power, @Arg(name = "duration",def = "25")int duration, @Arg(name = "height-above",def = "10")int height,@FlagArg("-cursor")boolean cursor) {
		Location loc = null;

		if (cursor) {
			loc = Players.getTargetLocation(p);
		} else {
			loc = p.getLocation();
		}

		loc.setY(loc.getY() + height);

		Shards.bomb(loc,power,duration);
	}

}
