package wfc;

import java.util.HashSet;
import java.util.Set;

public abstract class Tile<T> {

    private int[] position;
    private T state;

    private Set<T> possibleNeighbours;

    public Tile(int[] position, T state) {
        this.position = position;
        this.state = state;
        this.possibleNeighbours = new HashSet<>();
    }

    abstract void initNeighbours();

    public void setState(Object state) {
        this.state = (T) state;
    }

    abstract Set<Tile<T>> getAllowedNeighbours();

    abstract void updateNeighbours();



}
