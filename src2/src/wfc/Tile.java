package wfc;

import java.util.HashSet;
import java.util.Set;

public enum Tile {
    GROUND("0", new HashSet<>()) {
        @Override
        void addAdjacencyTiles() {
            getPotentialAdjacency().add(GROUND);
            getPotentialAdjacency().add(WATER);
            getPotentialAdjacency().add(FOREST);
        }
    },
    WATER("1", new HashSet<>()) {
        @Override
        void addAdjacencyTiles() {
            getPotentialAdjacency().add(WATER);
            getPotentialAdjacency().add(GROUND);
        }
    },
    FOREST("2", new HashSet<>()) {
        @Override
        void addAdjacencyTiles() {
            getPotentialAdjacency().add(FOREST);
            getPotentialAdjacency().add(WATER);
        }
    };

    private final String representation;

    private final Set<Tile> potentialAdjacency;

    Tile (String representation, Set<Tile> potentialAdjacency) {
        this.representation = representation;
        this.potentialAdjacency = potentialAdjacency;
    }

    abstract void addAdjacencyTiles();

    public Set<Tile> getPotentialAdjacency() {
        return potentialAdjacency;
    }

    public String getRepresentation() {
        return representation;
    }
}
