package cottle.jordan.bettertrees;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public class TreeTracker {
    private LinkedBlock treeRoot;

    public TreeTracker(Block startBlock){
        treeRoot = new LinkedBlock(startBlock);
    }

    public void clear(){
        this.treeRoot.clear();
    }

    public void transform(Material to){
        transform(treeRoot, to);
    }

    public boolean rootEquals(Block block){
        return treeRoot.equals(block);
    }

    private void transform(LinkedBlock treeNode, Material to){
        Block curr = treeNode.getBlock();

        if(to.equals(Material.AIR)){
            curr.breakNaturally(new ItemStack(Material.DIAMOND_AXE));
        }else{
            curr.setType(to);
        }

        for(LinkedBlock neighbor: treeNode.getNeighbors()){
            transform(neighbor, to);
        }
    }
}