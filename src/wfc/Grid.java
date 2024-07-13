package wfc;

import wfc.pattern.Tile;
import wfc.pattern.Tiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public abstract class Grid<T extends Cell> {

    T[][] grid;

    private final Set<Tile> allPossibleTiles;

    public abstract T createCell(int[] position);

    public Grid(Set<Tile> allPossibleTiles, Supplier<T[][]> gridSupplier) {
        grid = gridSupplier.get();
        this.allPossibleTiles = allPossibleTiles;
        this.init();
    }

    public void init() {
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                int[] cellPos = {i, j};
                grid[i][j] = createCell(cellPos);
            }
        }
    }

    public List<Cell> getNeighbourCandidates(int x, int y) {
        List<Cell> neighbors = new ArrayList<>();
        Map<String, Vector2i> neighbourOffsets = Tiles.getNeighbouringCandidates();

        for (Vector2i offset : neighbourOffsets.values()) {
            int nx = x + offset.x();
            int ny = y + offset.y();

            if (isWithinBounds(nx, ny)) {
                neighbors.add(getTileSafe(nx, ny));
            }
        }
        return neighbors;
    }

    public Vector2i getOffsetFromCell(Cell a, Cell b) {
        int dx = b.getPosition()[0] - a.getPosition()[0];
        int dy = b.getPosition()[1] - a.getPosition()[1];
        for (Vector2i vec : Tiles.getNeighbouringCandidates().values()) {
            if (vec.x() == dx && vec.y() == dy) {
                return vec;
            }
        }
        return null;
    }

    private boolean isWithinBounds(int x, int y) {
        return x >= 0 && x < getWidth() && y >= 0 && y < getHeight();
    }

    protected Cell getTile(int x, int y) {
        return grid[x][y];
    }

    public Cell getTileSafe(int x, int y) {
        int[] safePos = getSafePosition(x, y);
        return getTile(safePos[0], safePos[1]);
    }

    protected void setStateOfTile(Tile state, int x, int y) {
        grid[x][y].setState(state);
    }

    public void setStateOfTileSafe(Tile state, int x, int y) {
        int[] safePos = getSafePosition(x, y);
        setStateOfTile(state, safePos[0], safePos[1]);
    }


    private int[] getSafePosition(int x, int y) {
        int safeX, safeY;
        safeX = (x + grid.length) % grid.length;
        safeY = (y + grid[safeX].length) % grid[safeX].length;
        return new int[]{safeX, safeY};
    }

    public int getWidth() {
        return grid.length;
    }

    public int getHeight() {
        return grid[0].length;
    }

    public Set<Tile> getAllPossibleTiles() {
        return allPossibleTiles;
    }

    public void printWaveStates(int[][] entropyMap) {
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                System.out.print(" (");
                for (Tile t : getAllPossibleTiles()) {
                    if (getTile(i, j).getPotentialTiles().contains(t)) {
                        System.out.printf(" %s ", 1);
                    } else {
                        System.out.printf(" %s ", 0);
                    }
                }
                System.out.print(" | " + entropyMap[i][j] + " ");
                System.out.print(") ");
            }
            System.out.println();
        }
    }

}
