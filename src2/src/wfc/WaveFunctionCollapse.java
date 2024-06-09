package wfc;

import java.util.*;

public abstract class WaveFunctionCollapse {

    private Grid grid;

    private int[][] entropyMap;

    private final Set<Tile> allPossibleTiles;

    public WaveFunctionCollapse(Set<Tile> allPossibleTiles) {
        this.allPossibleTiles = allPossibleTiles;
    }

    public void init(Grid grid) {
        this.entropyMap = new int[grid.getWidth()][grid.getHeight()];
        this.grid = grid;
        collapse();

    }

    private void collapse() {
        initPotentialStatesOfGrid();
        setFixedStates();
        computeEntropyMap();
        while (true) {

            Cell<?> selectecCell = findLowestEntropyCell();
            if (selectecCell == null) break;
            int x = selectecCell.getPosition()[0];
            int y = selectecCell.getPosition()[1];
            // update immediate one, neighbouring cells will be updated later
            updateCell(x, y);
            propagateConstraints(selectecCell);
        }
    }

    private void propagateConstraints(Cell<?> parent) {
        int[] pos = parent.getPosition();
        List<Cell<?>> possibleNeighbours = getNeighbourCandidates(pos[0], pos[1]);
        for (Cell<?> neighbour : possibleNeighbours) {
            if (neighbour.compareTo(parent) == 0) {
                continue;
            }
            int[] neighbourPos = neighbour.getPosition();
            Set<Cell<?>> xyz = new HashSet<>(neighbour.getAllowedNeighbours());
            // update all neighbours
            updateCell(neighbourPos[0], neighbourPos[1]);

            if (!xyz.retainAll(neighbour.getAllowedNeighbours())) {
                continue;
            }

            propagateConstraints(neighbour);
        }
    }


    private List<Cell<?>> getNeighbourCandidates(int x, int y) {
        List<Cell<?>> neighbors = new ArrayList<>();
        if (x > 0) neighbors.add(grid.getTileSafe(x - 1, y));
        if (x < entropyMap.length - 1) neighbors.add(grid.getTileSafe(x + 1, y));
        if (y > 0) neighbors.add(grid.getTileSafe(x, y - 1));
        if (y < entropyMap[x].length - 1) neighbors.add(grid.getTileSafe(x, y + 1));
        return neighbors;
    }


    private void computeEntropyMap() {
        for (int i = 0; i < entropyMap.length; i++) {
            for (int j = 0; j < entropyMap[i].length; j++) {
                entropyMap[i][j] = grid.getTile(i, j).getAllowedNeighbours().size();
            }
        }
    }


    private void initPotentialStatesOfGrid() {
        for (int i = 0; i < grid.getWidth(); i++) {
            for (int j = 0; j < grid.getHeight(); j++) {
                grid.getTile(i, j).setPotentialTiles(allPossibleTiles);
            }
        }
    }

    private void setFixedStates(Triplet... states) {
        for(Triplet t : states) {
            Set<Tile> tiles = new HashSet<>(List.of(t.tiles()));
            grid.getTileSafe(t.x(), t.y()).removeSetsFromPotentialTiles(tiles);
        }
    }

    private Cell<?> findLowestEntropyCell() {
        return null;
    }

    private void updateEntropyMap(int x, int y) {
        entropyMap[x][y] = grid.getTile(x, y).getAllowedNeighbours().size();
    }

    private void updateCell(int x, int y) {
        grid.getTile(x, y).updateNeighbours();
        updateEntropyMap(x, y);
    }


}
