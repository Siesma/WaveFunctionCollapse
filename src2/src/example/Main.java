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

        int n = 10;

        TileGrid grid = new TileGrid(n, n, set);
        WaveFunctionCollapse wfc = new WaveFunctionCollapse() {};
        wfc.init(grid);


        System.out.println(grid.toString());

    }

}
