package example.tiles;

import wfc.pattern.Tile;
import wfc.pattern.Tiles;

import java.util.HashSet;
import java.util.Set;

public class DeepWater  implements Tile {

    static HashSet<Tile> adjacencies;

    static {
        adjacencies = new HashSet<>();
    }

    @Override
    public Set<Tile> getPotentialAdjacency() {
        adjacencies.add(Tiles.getTile("Water"));
        adjacencies.add(Tiles.getTile("DeepWater"));
        return adjacencies;
    }

    @Override
    public String getRepresentation() {
        return "DeepWater";
    }
}
