package wfc;

import wfc.pattern.Tile;

import java.util.*;

public abstract class WaveFunctionCollapse {

    private Grid grid;

    private int[][] entropyMap;

    public void init(Grid grid) {
        this.entropyMap = new int[grid.getWidth()][grid.getHeight()];
        this.grid = grid;
        collapse();
    }

    private void collapse() {
        initPotentialStatesOfGrid();
        setFixedStates();
        computeEntropyMap();
        while (!hasFullyCollapsed()) {
            Cell selectecCell = findRandomLowestEntropyCell();

            if (selectecCell == null) break;
            collapseState(selectecCell.getPosition()[0], selectecCell.getPosition()[1]);

            updateNeighbours(selectecCell.getPosition()[0], selectecCell.getPosition()[1]);
            propagateConstraints(selectecCell);
        }
    }

    private void propagateConstraints(Cell parent) {
        int[] pos = parent.getPosition();
        List<Cell> possibleNeighbours = grid.getNeighbourCandidates(pos[0], pos[1]);
        for (Cell neighbour : possibleNeighbours) {
            if (neighbour.compareTo(parent) == 0) {
                continue;
            }
            if (neighbour.isCollapsed()) {
                continue;
            }
            int[] neighbourPos = neighbour.getPosition();
            Set<Tile> previousState = new HashSet<>(neighbour.getAllowedNeighbours());
            updateNeighbours(neighbourPos[0], neighbourPos[1]);

            // retainAll??
            if (!previousState.equals(neighbour.getAllowedNeighbours())) {
                propagateConstraints(neighbour);
            }
        }
    }

    private boolean hasFullyCollapsed() {
        for (int i = 0; i < grid.getWidth(); i++) {
            for (int j = 0; j < grid.getHeight(); j++) {
                if (!grid.getTile(i, j).isCollapsed()) {
                    return false;
                }
            }
        }
        return true;
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
                grid.getTile(i, j).setPotentialTiles(grid.getAllPossibleTiles());
            }
        }
    }

    private void setFixedStates(Triplet... states) {
//        for (Triplet t : states) {
//            Set<Tile> tiles = new HashSet<>(List.of(t.tiles()));
//            grid.getTileSafe(t.x(), t.y()).removeSetsFromPotentialTiles(tiles);
//        }
    }

    private Cell findRandomLowestEntropyCell() {
        List<Cell> lowestEntropyCells = new ArrayList<>();
        int lowestEntropy = Integer.MAX_VALUE;

        for (int x = 0; x < entropyMap.length; x++) {
            for (int y = 0; y < entropyMap[x].length; y++) {
                int entropy = entropyMap[x][y];
                Cell cell = grid.getTile(x, y);
                if (cell.isCollapsed()) {
                    continue;
                }
                if (entropy < lowestEntropy) {
                    lowestEntropy = entropy;
                    lowestEntropyCells.clear();
                    lowestEntropyCells.add(cell);
                } else if (entropy == lowestEntropy) {
                    lowestEntropyCells.add(cell);
                }
            }
        }

        if (lowestEntropyCells.isEmpty()) {
            return null; // If no cell was found, return null
        }

        // Randomly select one of the lowest entropy cells
        Random random = new Random();
        return lowestEntropyCells.get(random.nextInt(lowestEntropyCells.size()));
    }

    private void collapseState(int x, int y) {
        grid.getTile(x, y).fixState();
    }

    private void updateNeighbours (int x, int y) {
        grid.getTile(x, y).updateNeighbours(grid, entropyMap);
    }


}
