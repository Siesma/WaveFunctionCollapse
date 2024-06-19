package example.tiles;

import wfc.Vector2i;
import wfc.pattern.Tile;
import wfc.pattern.Tiles;

import java.util.Set;

public class Water extends Tile{

    public Water () {
        super();
    }
    @Override
    public void initAdjacency() {
        adjacencies.get(defaultNeighbouringVector).add(Tiles.getTile("Ground"));
        adjacencies.get(defaultNeighbouringVector).add(Tiles.getTile("Water"));

        for(Vector2i vec : Tiles.getNeighbouringCandidates().values()) {
            adjacencies.get(vec).add(Tiles.getTile("Ground"));
            adjacencies.get(vec).add(Tiles.getTile("Water"));
        }
    }

    @Override
    public Set<Tile> getPotentialAdjacency(Vector2i neighbouring) {
        return adjacencies.get(neighbouring);
    }

    @Override
    public Set<Tile> getPotentialAdjacency() {
        return adjacencies.get(defaultNeighbouringVector);
    }

    @Override
    public String getRepresentation() {
        return "Water";
    }
}
