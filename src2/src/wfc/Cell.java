package wfc;

import java.util.HashSet;
import java.util.Set;

public abstract class Cell<T extends Tile> implements Comparable<Cell<?>> {

    private int[] position;
    private T state;

    private Set<Tile> potentialTiles;

    public Cell(int[] position, T state) {
        this.position = position;
        this.state = state;
        this.potentialTiles = new HashSet<>();
    }

    protected void setPotentialTiles (Set<Tile> potentialTiles) {
        this.potentialTiles.addAll(potentialTiles);
    }

    protected void removeSetsFromPotentialTiles(Set<Tile> alteredStates) {
        this.potentialTiles.removeAll(alteredStates);
    }

    abstract boolean isCollapsed ();

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
