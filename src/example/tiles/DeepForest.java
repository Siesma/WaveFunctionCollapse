package example.tiles;

import wfc.Vector2i;
import wfc.pattern.Tile;
import wfc.pattern.Tiles;

import javax.print.attribute.HashPrintServiceAttributeSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class DeepForest  implements Tile {

    static HashMap<Vector2i, HashSet<Tile>> adjacencies;

    static {
        adjacencies = new HashMap<>();
        adjacencies.put(defaultNeighbouringVector, new HashSet<>());
        for(Vector2i vec : Tiles.getNeighbouringCandidates().values()) {
            adjacencies.put(vec, new HashSet<>());
        }
    }


    @Override
    public Set<Tile> getPotentialAdjacency(Vector2i neighbouring) {
        return null;
    }

    @Override
    public Set<Tile> getPotentialAdjacency() {
        adjacencies.get(defaultNeighbouringVector).add(Tiles.getTile("Forest"));
        adjacencies.get(defaultNeighbouringVector).add(Tiles.getTile("DeepForest"));
        return adjacencies.get(defaultNeighbouringVector);
    }

    @Override
    public String getRepresentation() {
        return "DeepForest";
    }
}
