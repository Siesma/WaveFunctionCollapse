package wfc;

import wfc.pattern.Tile;

import java.util.*;

public abstract class WaveFunctionCollapse {

    private Grid<? extends Cell> grid;

    private int[][] entropyMap;

    private PriorityQueue<Cell> priorityQueue;
    private Set<Cell> inQueue;

    public void init(Grid<? extends Cell> grid) {
        this.entropyMap = new int[grid.getWidth()][grid.getHeight()];
        this.grid = grid;
        this.priorityQueue = new PriorityQueue<>(Comparator.comparingInt(cell -> entropyMap[cell.getPosition()[0]][cell.getPosition()[1]]));
        this.inQueue = new HashSet<>();
        initPotentialStatesOfGrid();
    }

    public boolean collapse() {
        // call setFixedStates before this!!!
        computeEntropyMap();
        while (!hasFullyCollapsed()) {
            Cell selectecCell = findRandomLowestEntropyCell();

            if (selectecCell == null) break;
            boolean successful = collapseState(selectecCell.getPosition()[0], selectecCell.getPosition()[1]);

            if (!successful) {
                return false;
            }

            updateNeighbours(selectecCell.getPosition()[0], selectecCell.getPosition()[1]);
            propagateConstraints(selectecCell);
            recomputePriorities();
        }
        return true;
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
            // TODO: Change Set<Tile> to HashMap<Vector2i, Set<Tile>> for relative adjacency

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
                entropyMap[i][j] = grid.getTile(i, j).computeEntropy();
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

    public void setFixedStates(Triplet... states) {
        for (Triplet t : states) {
            Set<Tile> tiles = new HashSet<>(List.of(t.tiles()));
            grid.getTileSafe(t.x(), t.y()).retainSetsFromPotentialTiles(tiles);
            updateNeighbours(t.x(), t.y());
        }
    }

    private Cell findRandomLowestEntropyCell() {
        if (priorityQueue.isEmpty()) {
            return null;
        }
        List<Cell> cellsWithLowestEntropy = new ArrayList<>();
        int lowestEntropy = computeEntropy(priorityQueue.peek());
        while (true) {
            if (priorityQueue.isEmpty()) {
                break;
            }
            Cell curCell = priorityQueue.peek();
            int curEntropy = computeEntropy(curCell);
            if (curEntropy != lowestEntropy) {
                break;
            }
            cellsWithLowestEntropy.add(priorityQueue.poll());
        }

        Random random = new Random();
        Cell selectedCell = cellsWithLowestEntropy.get(random.nextInt(cellsWithLowestEntropy.size()));
        inQueue.remove(selectedCell);
        cellsWithLowestEntropy.remove(selectedCell);

        priorityQueue.addAll(cellsWithLowestEntropy);
        return selectedCell;

    }

    /**
     * Deprecated method, was previously @findRandomLowestEntropyCell, but has been replaced
     * @return
     */
    @Deprecated
    private Cell getFirstLowestEntropyCell() {
        while (!priorityQueue.isEmpty()) {
            Cell cell = priorityQueue.poll();
            inQueue.remove(cell);
            if (!cell.isCollapsed()) {
                return cell;
            }
        }
        return null;
    }

    private int computeEntropy(Cell cell) {
        return entropyMap[cell.getPosition()[0]][cell.getPosition()[1]];
    }

    private boolean collapseState(int x, int y) {
        return grid.getTile(x, y).fixState();
    }

    private void updateNeighbours(int x, int y) {
        grid.getTile(x, y).updateNeighbours(priorityQueue, inQueue, grid, entropyMap);
    }

    private void recomputePriorities() {
        List<Cell> allCells = new ArrayList<>(priorityQueue);
        priorityQueue.clear();
        priorityQueue.addAll(allCells);
    }
}
