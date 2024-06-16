package wfc;

import wfc.pattern.Tile;
import wfc.pattern.Tiles;

import java.util.*;

public abstract class WaveFunctionCollapse {

    private Grid grid;

    private int[][] entropyMap;

    private PriorityQueue<Cell> priorityQueue;
    private Set<Cell> inQueue;

    public void init(Grid grid) {
        this.entropyMap = new int[grid.getWidth()][grid.getHeight()];
        this.grid = grid;
        this.priorityQueue = new PriorityQueue<>(Comparator.comparingInt(cell -> entropyMap[cell.getPosition()[0]][cell.getPosition()[1]]));
        this.inQueue = new HashSet<>();
        Tiles.initDefaultNeighbouringCandidates();
    }

    public void collapse() {
        // call setFixedStates before this!!!
        initPotentialStatesOfGrid();
//        setFixedStates(
//            new Triplet(0, 0, Tiles.getTile("Forest")),
//            new Triplet(1, 0, Tiles.getTile("Forest")),
//            new Triplet(2, 0, Tiles.getTile("Forest")),
//            new Triplet(3, 0, Tiles.getTile("Forest")),
//            new Triplet(4, 0, Tiles.getTile("Forest")),
//            new Triplet(5, 0, Tiles.getTile("Forest")),
//            new Triplet(6, 0, Tiles.getTile("Forest"))
//        );
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
                if (!grid.getTile(i, j).isCollapsed()) {
                    priorityQueue.offer(grid.getTile(i, j));
                    inQueue.add(grid.getTile(i, j));
                }
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
        for (Triplet t : states) {
            Set<Tile> tiles = new HashSet<>(List.of(t.tiles()));
            grid.getTileSafe(t.x(), t.y()).retainSetsFromPotentialTiles(tiles);
        }
    }

    private Cell findRandomLowestEntropyCell() {
        while (!priorityQueue.isEmpty()) {
            Cell cell = priorityQueue.poll(); // Get the cell with the lowest entropy
            inQueue.remove(cell); // Remove it from the inQueue set

            // Check if the cell has not collapsed yet
            if (!cell.isCollapsed()) {
                return cell;
            }
        }
        return null; // If the queue is empty and no cell is found
    }


    private void collapseState(int x, int y) {
        grid.getTile(x, y).fixState();
    }

    private void updateNeighbours(int x, int y) {
        grid.getTile(x, y).updateNeighbours(priorityQueue, inQueue, grid, entropyMap);
    }


}
