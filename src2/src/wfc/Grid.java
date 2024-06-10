package wfc;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class Grid {

    Cell<?>[][] grid;

    private final Set<Tile> allPossibleTiles;

    public Grid(int w, int h, Set<Tile> allPossibleTiles) {
        grid = new Cell[w][h];
        this.allPossibleTiles = allPossibleTiles;
        this.init();
    }

    public void init () {
        for(int i = 0; i < getWidth(); i++) {
            for(int j = 0; j < getHeight(); j++) {
                int[] cellPos = {i, j};
                grid[i][j] = new Cell<Tile>(cellPos) {};
            }
        }
    }


    public List<Cell<?>> getNeighbourCandidates(int x, int y) {
        List<Cell<?>> neighbors = new ArrayList<>();
        if (x > 0) neighbors.add(getTileSafe(x - 1, y));
        if (x < getWidth() - 1) neighbors.add(getTileSafe(x + 1, y));
        if (y > 0) neighbors.add(getTileSafe(x, y - 1));
        if (y < getHeight() - 1) neighbors.add(getTileSafe(x, y + 1));
        return neighbors;
    }

    protected Cell<?> getTile(int x, int y) {
        return grid[x][y];
    }

    public Cell<?> getTileSafe(int x, int y) {
        int[] safePos = getSafePosition(x, y);
        return getTile(safePos[0], safePos[1]);
    }

    protected void setTile(Cell<?> newState, int x, int y) {
        grid[x][y] = newState;
    }


    public void setTileSafe(Cell<?> newState, int x, int y) {
        int[] safePos = getSafePosition(x, y);
        setTile(newState, safePos[0], safePos[1]);
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
}
