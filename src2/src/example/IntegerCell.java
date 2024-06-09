package example;

import wfc.Cell;
import wfc.Grid;
import wfc.Tile;

import java.util.HashSet;
import java.util.Set;

public class IntegerCell extends Cell<Tile> {
    public IntegerCell(int[] position, Tile state) {
        super(position, state);
    }

    @Override
    public boolean isCollapsed() {
        return potentialTiles.size() == 1;
    }

    @Override
    public Set<Tile> getAllowedNeighbours(Grid grid) {
        Set<Tile> out = new HashSet<>(grid.getAllPossibleTiles());
        out.retainAll(getState().getPotentialAdjacency());
        return out;
    }

    @Override
    public void updateNeighbours() {
        this.potentialTiles.retainAll(getState().getPotentialAdjacency());
    }
}
