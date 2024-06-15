package wfc;

import wfc.pattern.Tile;

import java.util.*;

public abstract class Cell implements Comparable<Cell> {

    private int[] position;
    private Tile state;
    protected List<Tile> potentialTiles;

    private int numTiles = -1;


    public Cell(int[] position) {
        this.position = position;
        this.potentialTiles = new ArrayList<>();
        this.numTiles = 0;
    }

    protected void setPotentialTiles(Set<Tile> potentialTiles) {
        this.potentialTiles.addAll(potentialTiles);
        this.numTiles = potentialTiles.size();
    }

    protected void removeSetsFromPotentialTiles(Set<Tile> alteredStates) {
        this.potentialTiles.removeAll(alteredStates);
        this.numTiles = potentialTiles.size();
    }

    public boolean isCollapsed() {
        boolean collapsed = numTiles == 1;
        if (collapsed) {
            this.state = potentialTiles.get(0);
        }
        return collapsed;
    }

    public void setState(Tile state) {
        this.state = state;
    }

    public Tile getState() {
        return state;
    }

    public void fixState() {
        Random random = new Random();

        List<Tile> tileList = new ArrayList<>(potentialTiles);

        int index = random.nextInt(tileList.size());

        Tile newState = tileList.get(index);

        setState(newState);

        potentialTiles.retainAll(Collections.singleton(newState));
        this.numTiles = 1;
    }


    public Set<Tile> getAllowedNeighbours() {
        Set<Tile> allowedNeighbours = new HashSet<>();

        for (Tile potentialTile : potentialTiles) {

            Set<Tile> compatibleTiles = potentialTile.getPotentialAdjacency();

            allowedNeighbours.addAll(compatibleTiles);
        }

        return allowedNeighbours;
    }


    public void updateNeighbours(Grid grid, int[][] entropyMap) {

        updateEntropy(entropyMap);

        List<Cell> neighbours = grid.getNeighbourCandidates(position[0], position[1]);
        for(Cell neighbour : neighbours) {
            if(neighbour.isCollapsed()) {
                continue;
            }
            neighbour.potentialTiles.retainAll(this.getState().getPotentialAdjacency());
            neighbour.updateEntropy(entropyMap);
        }

    }

    public int[] getPosition() {
        return position;
    }

    public List<Tile> getPotentialTiles() {
        return potentialTiles;
    }

    private void updateEntropy (int[][] entropyMap) {
        entropyMap[position[0]][position[1]] = computeEntropy();
    }

    private int computeEntropy () {
        return getPotentialTiles().size() - 1;
    }

    @Override
    public int compareTo(Cell other) {
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
