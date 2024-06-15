package wfc;

import java.util.*;

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

        List<Tile> tileList = new ArrayList<>(potentialTiles);

        int index = random.nextInt(tileList.size());

        Tile newState = tileList.get(index);

        setState(newState);

        potentialTiles.retainAll(Collections.singleton(newState));
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
        if(isCollapsed()) {
            return;
        }
        List<Cell<?>> neighbours = grid.getNeighbourCandidates(position[0], position[1]);

        List<Tile> allowedOnes = new ArrayList<>(grid.getAllPossibleTiles());

        for(Cell<?> neighbour : neighbours) {
            if(neighbour.isCollapsed()) {
                continue;
            }
            allowedOnes.retainAll(neighbour.getAllowedNeighbours());
        }

        this.potentialTiles.retainAll(allowedOnes);

    }

    private Set<Tile> notIn(Set<Tile> allowed, Set<Tile> neighbourPotential) {
        Set<Tile> notIn = new HashSet<>(neighbourPotential);
        notIn.removeAll(allowed);
        return notIn;
    }

    public int[] getPosition() {
        return position;
    }

    public Set<Tile> getPotentialTiles() {
        return potentialTiles;
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
