package wfc;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public abstract class Cell<T extends Tile> implements Comparable<Cell<?>> {

    private int[] position;
    private Tile state;
    protected Set<Tile> potentialTiles;

    public Cell(int[] position) {
        this.position = position;
        this.potentialTiles = new HashSet<>();
    }

    protected void setPotentialTiles(Set<Tile> potentialTiles) {
        this.potentialTiles.addAll(potentialTiles);
    }

    protected void removeSetsFromPotentialTiles(Set<Tile> alteredStates) {
        this.potentialTiles.removeAll(alteredStates);
    }

    public boolean isCollapsed() {
        boolean collapsed = potentialTiles.size() == 1;
        if (collapsed) {
            this.state = potentialTiles.iterator().next();
        }
        return collapsed;
    }

    public void setState(Tile state) {
        this.state = state;
    }

    public Tile getState() {
        if (!isCollapsed()) {
            return Tile.GROUND;
        }
        return state;
    }

    public void fixState() {
        Random random = new Random();
        int index = random.nextInt(0, potentialTiles.size());
        Tile newState = potentialTiles.iterator().next();
        for (int i = 0; i < index - 1; i++) {
            newState = potentialTiles.iterator().next();
        }
        System.out.println("\t Setting tile to: " + newState.getRepresentation());
        setState(newState);
        HashSet<Tile> fixedTile = new HashSet<>();
        fixedTile.add(newState);
        this.potentialTiles.retainAll(fixedTile);
    }

    public Set<Tile> getAllowedNeighbours() {
        Set<Tile> allowedNeighbours = new HashSet<>();

        for (Tile potentialTile : potentialTiles) {

            Set<Tile> compatibleTiles = potentialTile.getPotentialAdjacency();

            allowedNeighbours.addAll(compatibleTiles);
        }

        return allowedNeighbours;
    }


    public void updateNeighbours(Grid grid) {
        List<Cell<?>> neighbours = grid.getNeighbourCandidates(position[0], position[1]);

        for (Cell<?> neighbour : neighbours) {
            Set<Tile> allowedNeighbours = getAllowedNeighbours();
            neighbour.removeSetsFromPotentialTiles(notIn(allowedNeighbours, neighbour.potentialTiles));
        }
    }

    private Set<Tile> notIn(Set<Tile> allowed, Set<Tile> neighbourPotential) {
        Set<Tile> notIn = new HashSet<>(neighbourPotential);
        notIn.removeAll(allowed);
        return notIn;
    }

    public int[] getPosition() {
        return position;
    }

    @Override
    public int compareTo(Cell<?> other) {
        int mx = getPosition()[0];
        int my = getPosition()[1];
        int ox = getPosition()[0];
        int oy = getPosition()[1];
        if (mx == ox && my == oy) {
            return 0;
        } else {
            if (state.equals(other.state)) {
                return 1;
            } else {
                return -1;
            }
        }
    }
}
