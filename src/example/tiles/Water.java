package example.tiles;

import wfc.Vector2i;
import wfc.pattern.Tile;
import wfc.pattern.Tiles;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Water implements Tile {

    static HashMap<Vector2i, HashSet<Tile>> adjacencies;

    @Override
    public void initAdjacencies() {
        adjacencies = new HashMap<>();
        adjacencies.put(defaultNeighbouringVector, new HashSet<>());
        for(Vector2i vec : Tiles.getNeighbouringCandidates().values()) {
            adjacencies.put(vec, new HashSet<>());
        }

        adjacencies.get(defaultNeighbouringVector).add(Tiles.getTile("Ground"));
        adjacencies.get(defaultNeighbouringVector).add(Tiles.getTile("Water"));

        for(Vector2i vec : Tiles.getNeighbouringCandidates().values()) {
            adjacencies.get(vec).add(Tiles.getTile("Ground"));
            adjacencies.get(vec).add(Tiles.getTile("Water"));
        }
    }

    @Override
    public Set<Tile> getPotentialAdjacency(Vector2i neighbouring) {
        System.out.println();
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
