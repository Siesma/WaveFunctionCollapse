package wfc;

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
        List<Cell<?>> possibleNeighbours = grid.getNeighbourCandidates(pos[0], pos[1]);
        for (Cell<?> neighbour : possibleNeighbours) {
            if (neighbour.compareTo(parent) == 0) {
                continue;
            }
            if(neighbour.isCollapsed()) {
                continue;
            }
            int[] neighbourPos = neighbour.getPosition();
            Set<Tile> xyz = new HashSet<>(neighbour.getAllowedNeighbours());
            // update all neighbours
            updateCell(neighbourPos[0], neighbourPos[1]);

            // retainAll??
            if (!xyz.equals(neighbour.getAllowedNeighbours())) {
                propagateConstraints(neighbour);
            }

            propagateConstraints(neighbour);
        }
    }

    private boolean hasFullyCollapsed () {
        for (int i = 0; i < grid.getWidth(); i++) {
            for (int j = 0; j < grid.getHeight(); j++) {
                if(!grid.getTile(i, j).isCollapsed()) {
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
        for(Triplet t : states) {
            Set<Tile> tiles = new HashSet<>(List.of(t.tiles()));
            grid.getTileSafe(t.x(), t.y()).removeSetsFromPotentialTiles(tiles);
        }
    }

    private Cell<?> findLowestEntropyCell() {
        Cell<?> lowestEntropyCell = null;
        int lowestEntropy = Integer.MAX_VALUE;

        for (int x = 0; x < entropyMap.length; x++) {
            for (int y = 0; y < entropyMap[x].length; y++) {
                int entropy = entropyMap[x][y];
                Cell<?> cell = grid.getTile(x, y);
                if(cell.isCollapsed()) {
                    continue;
                }
                if (entropy < lowestEntropy) {
                    lowestEntropy = entropy;
                    lowestEntropyCell = cell;
                }
            }
        }
        return lowestEntropyCell;
    }


    private void updateEntropyMap(int x, int y) {
        entropyMap[x][y] = grid.getTile(x, y).getAllowedNeighbours().size();
    }

    private void updateCell(int x, int y) {
        System.out.printf("Updating tile (%s, %s):\n", x, y);

        grid.printWaveStates();
        grid.getTile(x, y).fixState();
        System.out.println(grid.getTile(x, y).isCollapsed());
        grid.printWaveStates();

        grid.getTile(x, y).updateNeighbours(grid);
        updateEntropyMap(x, y);
    }


}
