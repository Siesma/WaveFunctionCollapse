package wfc;

import java.util.Set;

public abstract class Tile {

    private final String representation;

    public Tile (String representation) {
        this.representation = representation;
    }

    abstract Set<?> canBeAdjacentTo ();


}
