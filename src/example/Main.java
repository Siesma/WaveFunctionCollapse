package example;

import wfc.WaveFunctionCollapse;
import wfc.pattern.Tile;
import wfc.pattern.Tiles;
import wfc.pattern.tiles.Forest;
import wfc.pattern.tiles.Ground;
import wfc.pattern.tiles.Water;

import java.util.HashSet;

public class Main {

    public static void main(String[] args) {
        Tile forest = new Forest();
        Tile ground = new Ground();
        Tile water = new Water();

        Tiles.register(forest, forest.getRepresentation());
        Tiles.register(ground, ground.getRepresentation());
        Tiles.register(water, water.getRepresentation());

        HashSet<Tile> set = Tiles.allTiles();

        int n = 10;

        int maxTries = 1;

        int tries = 0;

        while (tries++ < maxTries) {
            System.out.println("Try: " + tries);
            try {

                TileGrid grid = new TileGrid(n, n, set);

                long start = System.currentTimeMillis();

                WaveFunctionCollapse wfc = new WaveFunctionCollapse() {
                };
                wfc.init(grid);

                long end = System.currentTimeMillis();

                System.out.println(grid.toString());

                System.out.printf("It took %s ms after %s tries", (end - start), tries);
                return;
            } catch (Exception e) {

                e.printStackTrace();

            }
        }


    }

}
