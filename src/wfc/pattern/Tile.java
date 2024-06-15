package wfc.pattern;

import java.util.Set;

public interface Tile {

    Set<Tile> getPotentialAdjacency();
    default String getRepresentation () {
        return getClass().getSimpleName();
    }

}
