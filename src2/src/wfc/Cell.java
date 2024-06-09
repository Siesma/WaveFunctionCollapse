package wfc;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public abstract class Cell<T> implements Comparable<Cell<?>> {

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

    public int[] getPosition() {
        return position;
    }

    @Override
    public int compareTo (Cell<?> other) {
        int mx = getPosition()[0];
        int my = getPosition()[1];
        int ox = getPosition()[0];
        int oy = getPosition()[1];
        if(mx == ox && my == oy) {
            return 0;
        } else {
            if(state.equals(other.state)) {
                return 1;
            } else {
                return -1;
            }
        }
    }
}
