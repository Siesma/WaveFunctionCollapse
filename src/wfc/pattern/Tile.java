package wfc.pattern;

import wfc.Vector2i;

import java.util.HashMap;
import java.util.Set;

public interface Tile {

//    HashMap<Vector2i, Set<Tile>> allAdjacenct();
//
//    Set<Tile> getPotentialAdjacency (Vector2i neighbouring);

    Set<Tile> getPotentialAdjacency();

    default String getRepresentation() {
        return getClass().getSimpleName();
    }

    // Can be useful for overwriting.
    default String getDisplayName() {
        return getRepresentation();
    }

}
