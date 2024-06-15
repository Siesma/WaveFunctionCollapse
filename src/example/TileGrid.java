package example;

import wfc.Cell;
import wfc.Grid;
import wfc.pattern.Tile;

import java.util.Set;

public class TileGrid extends Grid {
    public TileGrid(int w, int h, Set<Tile> allPossibleTiles) {
        super(w, h, allPossibleTiles);
    }

    @Override
    public String toString () {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i<  getWidth(); i++) {
            for(int j = 0; j < getHeight(); j++) {
                builder.append(getTile(i,j).getState().getRepresentation());
                builder.append(" ");
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}
