package cottle.jordan.bettertrees;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class LinkedBlock implements Comparable<LinkedBlock>{

    private Block source;
    private LinkedBlock parent = null;
    private Collection<Material> linkTo;
    private Collection<LinkedBlock> neighbors;
    private Map<Location, LinkedBlock> seen;
    private final int range;

    private int depth;

    public LinkedBlock(Block start){
        this(start, null, new HashMap<Location, LinkedBlock>());
    }

    private LinkedBlock(Block link, LinkedBlock parent, Map<Location, LinkedBlock> seen){
        this.seen = seen;
        seen.put(link.getLocation(), this);

        this.parent = parent;
        if (parent == null){
            this.depth = 0;
        }else{    
            this.depth = parent.depth + 1;
        }
        
        source = link;
        
        range = 1;
        linkTo = EnumSet.of(source.getType());
        neighbors = new ArrayList<LinkedBlock>();
        addNeighbors();
    }

    public int compareTo(LinkedBlock other){
        if (!linkTo.contains(other.source.getType())){
            throw new IllegalArgumentException("Cannot compare LinkedBlocks that won't connect!");
        }

        return other.depth - this.depth;
    }

    public void remove(){
        this.parent.neighbors.remove(this);

        for(LinkedBlock neighbor: neighbors){
            if(neighbor.parent.equals(this)){
                neighbor.setParent(null);
            }
        }
    }

    private void setParent(LinkedBlock other){
        this.parent = other;

        if(other != null){
            this.depth = other.depth+1;
        }
    }

    public boolean hasSeen(Block other){
        return seen.containsKey(other.getLocation());
    }

    public boolean linkableTo(Block other){
        return linkTo.contains(other.getType());
    }

    public Collection<LinkedBlock> getNeighbors(){
        return neighbors;
    }

    public Block getBlock(){
        return source;
    }

    private boolean linkTo(Block other){
        if (hasSeen(other)){
            System.out.println("Duplicate found!");
            LinkedBlock duplicate = seen.get(other.getLocation());

            // Check if quicker path to this block
            if(this.depth < duplicate.depth-1){
                System.out.println("Updating parent!");
                duplicate.setParent(this);
                return true;
            }

            return false;
        }

        LinkedBlock neighbor = new LinkedBlock(other, this, this.seen);
        neighbors.add(neighbor);
        return true;
    }

    private void addNeighbors(){
        for(int x = -range; x < range; x++){
            for(int y = -range; y < range; y++){
                for(int z = -range; z < range; z++){
                    Block check = source.getRelative(x, y, z);
                    if(linkableTo(check)){
                        linkTo(check);
                    }
                }
            }
        }
    }
}