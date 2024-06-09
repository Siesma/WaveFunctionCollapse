package wfc;

public abstract class Grid {

    Tile<?>[][] grid;

    public Grid(int w, int h) {
        grid = new Tile[w][h];
    }

    protected Tile<?> getTile(int x, int y) {
        return grid[x][y];
    }

    public Tile<?> getTileSafe(int x, int y) {
        int[] safePos = getSafePosition(x, y);
        return getTile(safePos[0], safePos[1]);
    }

    protected void setTile(Tile<?> newState, int x, int y) {
        grid[x][y] = newState;
    }


    public void setTileSafe(Tile<?> newState, int x, int y) {
        int[] safePos = getSafePosition(x, y);
        setTile(newState, safePos[0], safePos[1]);
    }

    protected void setStateOfTile (Object state, int x, int y) {
        grid[x][y].setState(state);
    }

    public void setStateOfTileSafe (Object state, int x, int y) {
        int[] safePos = getSafePosition(x, y);
        setStateOfTile(state, safePos[0], safePos[1]);
    }


    private int[] getSafePosition(int x, int y) {
        int safeX, safeY;
        safeX = (x + grid.length) % grid.length;
        safeY = (y + grid[safeX].length) % grid[safeX].length;
        return new int[]{safeX, safeY};
    }

}
