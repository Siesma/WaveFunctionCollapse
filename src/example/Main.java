package example;

import wfc.WaveFunctionCollapse;
import wfc.pattern.Tile;
import wfc.pattern.Tiles;
import example.tiles.Forest;
import example.tiles.Ground;
import example.tiles.Water;

import java.util.HashSet;

public class Main {

    public static void main(String[] args) {
        Tile forest = new Forest();
        Tile ground = new Ground();
        Tile water = new Water();

        Tiles.registerTileCandidate(forest, forest.getRepresentation());
        Tiles.registerTileCandidate(ground, ground.getRepresentation());
        Tiles.registerTileCandidate(water, water.getRepresentation());

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
                wfc.collapse();

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
