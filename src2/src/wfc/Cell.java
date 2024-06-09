package wfc;

import java.util.HashSet;
import java.util.Set;

public abstract class Cell<T> {

    private int[] position;
    private T state;

    private Set<T> possibleNeighbours;

    public Cell(int[] position, T state) {
        this.position = position;
        this.state = state;
        this.possibleNeighbours = new HashSet<>();
    }

    abstract void initNeighbours();

    public void setState(Object state) {
        this.state = (T) state;
    }

    abstract Set<Cell<T>> getAllowedNeighbours();

    abstract void updateNeighbours();



}
