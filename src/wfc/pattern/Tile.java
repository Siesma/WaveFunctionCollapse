package wfc.pattern;

import wfc.Vector2i;

import java.util.HashMap;
import java.util.Set;

public interface Tile {

    static Vector2i defaultNeighbouringVector = new Vector2i(0, 0);

    Set<Tile> getPotentialAdjacency (Vector2i neighbouring);

    // TODO: Update getPotentialAdjacency method to not be updated >every< call.
    Set<Tile> getPotentialAdjacency();

    void initAdjacencies();

    default String getRepresentation() {
        return getClass().getSimpleName();
    }

    // Can be useful for overwriting.
    default String getDisplayName() {
        return getRepresentation();
    }

}
