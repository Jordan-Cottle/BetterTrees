package cottle.jordan.bettertrees;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;

public class TreeListener implements Listener {
    @EventHandler
    public void onBreak(BlockBreakEvent event){
        Block block = event.getBlock();
        
        Player player = event.getPlayer();

        Location pos = block.getLocation();

        player.sendMessage("You broke a block at " + pos.toString() + "!");
    }

    @EventHandler
    public void onDamage(BlockDamageEvent event){
        Block block = event.getBlock();
        Player player = event.getPlayer();

        player.sendMessage("Oof that hurts!");
        System.out.println(block.getType().toString() + " was murdered!");
        event.setInstaBreak(true);
    }
}