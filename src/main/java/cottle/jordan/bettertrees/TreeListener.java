package cottle.jordan.bettertrees;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;

public class TreeListener implements Listener {
    private Map<Player, TreeTracker> trees;

    private static final Set<Material> woodTypes;

    static{
        woodTypes = EnumSet.of(
            Material.ACACIA_LOG,
            Material.BIRCH_LOG,
            Material.DARK_OAK_LOG,
            Material.JUNGLE_LOG,
            Material.OAK_LOG,
            Material.SPRUCE_LOG,

            Material.STRIPPED_ACACIA_LOG,
            Material.STRIPPED_BIRCH_LOG,
            Material.STRIPPED_DARK_OAK_LOG,
            Material.STRIPPED_JUNGLE_LOG,
            Material.STRIPPED_OAK_LOG,
            Material.STRIPPED_SPRUCE_LOG
            );
    }

    public TreeListener(){
        trees = new HashMap<Player, TreeTracker>();
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event){
        Player player = event.getPlayer();

        if(!trees.containsKey(player)){
            return;
        }
        TreeTracker tree = trees.get(player);
        Block block = event.getBlock();

        if(!tree.rootEquals(block)){
            return;
        }
        
        Location pos = block.getLocation();
        player.sendMessage("You broke a tree rooted at " + pos.toString() + "!");

        tree.transform(Material.AIR);
    }

    @EventHandler
    public void onDamage(BlockDamageEvent event){
        Block block = event.getBlock();
        if(!woodTypes.contains(block.getType())){
            return;
        }
        
        Player player = event.getPlayer();
        TreeTracker tree;
        if(!trees.containsKey(player)){
            player.sendMessage("Creating a new tree for you!");
            tree = new TreeTracker(block);

            trees.put(player, tree);
        }else{
            tree = trees.get(player);

            if(!tree.rootEquals(block)){
                tree.clear();
                trees.replace(player, new TreeTracker(block));
            }
        }
    }
}