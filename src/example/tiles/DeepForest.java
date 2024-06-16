package example.tiles;

import wfc.pattern.Tile;
import wfc.pattern.Tiles;

import java.util.HashSet;
import java.util.Set;

public class DeepForest  implements Tile {

    static HashSet<Tile> adjacencies;

    static {
        adjacencies = new HashSet<>();
    }

    @Override
    public Set<Tile> getPotentialAdjacency() {
        adjacencies.add(Tiles.getTile("Forest"));
        adjacencies.add(Tiles.getTile("DeepForest"));
        return adjacencies;
    }

    @Override
    public String getRepresentation() {
        return "DeepForest";
    }
}
