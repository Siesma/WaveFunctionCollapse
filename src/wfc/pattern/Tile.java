package wfc.pattern;

import wfc.Vector2i;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public abstract class Tile {

    protected HashMap<Vector2i, HashSet<Tile>> adjacencies;

    public Tile () {
        adjacencies = new HashMap<>();
        adjacencies.put(defaultNeighbouringVector, new HashSet<>());
        for (Vector2i vec : Tiles.getNeighbouringCandidates().values()) {
            adjacencies.put(vec, new HashSet<>());
        }
    }

    public Vector2i defaultNeighbouringVector = new Vector2i(0, 0);

    public abstract Set<Tile> getPotentialAdjacency(Vector2i neighbouring);

    // TODO: Update getPotentialAdjacency method to not be updated >every< call.
    public abstract Set<Tile> getPotentialAdjacency();

    public abstract void initAdjacencies();

    public String getRepresentation() {
        return getClass().getSimpleName();
    }

    // Can be useful for overwriting.
    public String getDisplayName() {
        return getRepresentation();
    }


}
