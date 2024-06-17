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

    protected void retainSetsFromPotentialTiles(Set<Tile> alteredStates) {
        this.potentialTiles.retainAll(alteredStates);
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

    public boolean fixState() {
        Random random = new Random();
        if(potentialTiles.isEmpty()) {
            System.err.printf("Cell (%s, %s) has no valid state\n", position[0], position[1]);
            return false;
        }
        List<Tile> tileList = new ArrayList<>(potentialTiles);

        int index = random.nextInt(tileList.size());

        Tile newState = tileList.get(index);

        setState(newState);

        potentialTiles.retainAll(Collections.singleton(newState));
        this.numTiles = 1;
        return true;
    }


    public Set<Tile> getAllowedNeighbours() {
        Set<Tile> allowedNeighbours = new HashSet<>();

        for (Tile potentialTile : potentialTiles) {

            Set<Tile> compatibleTiles = potentialTile.getPotentialAdjacency();

            allowedNeighbours.addAll(compatibleTiles);
        }

        return allowedNeighbours;
    }


    public void updateNeighbours(PriorityQueue<Cell> entropyPriorityQueue, Set<Cell> queue, Grid grid, int[][] entropyMap) {

        updateEntropy(entropyMap);

        List<Cell> neighbours = grid.getNeighbourCandidates(position[0], position[1]);
        for(Cell neighbour : neighbours) {
            if(neighbour.isCollapsed()) {
                continue;
            }
            if (!neighbour.isCollapsed() && !queue.contains(neighbour)) {
                entropyPriorityQueue.offer(neighbour);
                queue.add(neighbour);
            }


            Set<Tile> myAllowedNeighbours = new HashSet<>();

            Vector2i positionDifference = grid.getOffsetFromCell(this, neighbour);
            for (Tile tile : this.potentialTiles) {
                myAllowedNeighbours.addAll(tile.getPotentialAdjacency());
            }


            neighbour.potentialTiles.retainAll(myAllowedNeighbours);
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
