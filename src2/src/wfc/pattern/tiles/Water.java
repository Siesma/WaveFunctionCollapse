package wfc.pattern.tiles;

import wfc.pattern.Tile;
import wfc.pattern.Tiles;

import java.util.HashSet;
import java.util.Set;

public class Water implements Tile {

    static HashSet<Tile> adjacencies;

    static {
        adjacencies = new HashSet<>();
    }

    @Override
    public Set<Tile> getPotentialAdjacency() {
        adjacencies.add(Tiles.getTile("Ground"));
        adjacencies.add(Tiles.getTile("Water"));
        return adjacencies;
    }

    @Override
    public String getRepresentation() {
        return "Water";
    }
}
