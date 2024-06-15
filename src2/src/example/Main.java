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

        int n = 100;

        int maxTries = 1000;

        int tries = 0;

        while (tries++ < maxTries) {
            System.out.println("Try: " + tries);
            try {

                TileGrid grid = new TileGrid(n, n, set);

                long start = System.currentTimeMillis();

                WaveFunctionCollapse wfc = new WaveFunctionCollapse() {};
                wfc.init(grid);

                long end = System.currentTimeMillis();

                System.out.println(grid.toString());

                System.out.printf("It took %s ms after %s tries", (end - start), tries);
                return;
            } catch (Exception e) {
                continue;
            }
        }



    }

}
