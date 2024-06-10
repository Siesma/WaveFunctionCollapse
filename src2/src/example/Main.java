package example;

import wfc.Grid;
import wfc.Tile;
import wfc.WaveFunctionCollapse;

import java.util.HashSet;

public class Main {

    public static void main(String[] args) {
        HashSet<Tile> set = new HashSet<Tile>();

        set.add(Tile.FOREST);
        set.add(Tile.WATER);
        set.add(Tile.GROUND);

        TileGrid grid = new TileGrid(10, 10, set);
        WaveFunctionCollapse wfc = new WaveFunctionCollapse() {};
        wfc.init(grid);


        System.out.println(grid.toString());

    }

}
