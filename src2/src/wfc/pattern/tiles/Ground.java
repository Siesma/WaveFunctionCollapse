package wfc.pattern.tiles;

import wfc.pattern.Tile;
import wfc.pattern.Tiles;

import java.util.HashSet;
import java.util.Set;

public class Ground implements Tile {

    static HashSet<Tile> adjacencies;

    static {
        adjacencies = new HashSet<>();

        adjacencies.add(Tiles.getTile("2"));
        adjacencies.add(Tiles.getTile("1"));
        adjacencies.add(Tiles.getTile("0"));
    }

    @Override
    public Set<Tile> getPotentialAdjacency() {
        return adjacencies;
    }

    @Override
    public String getRepresentation() {
        return "0";
    }
}