import example.TileGrid;
import processing.core.PApplet;
import wfc.WaveFunctionCollapse;
import wfc.pattern.Tile;
import wfc.pattern.Tiles;
import example.tiles.Forest;
import example.tiles.Ground;
import example.tiles.Water;

import java.util.HashSet;

public class Main extends PApplet {

    private TileGrid grid;


    HashSet<Tile> allExistingTiles;

    public static void main(String[] args) {
        PApplet.main("Main", args);
    }

    @Override
    public void draw() {
        background(0); // Clear the background with black

        int w = width / grid.getWidth();  // Calculate width of each cell
        int h = height / grid.getHeight(); // Calculate height of each cell

        for (int i = 0; i < grid.getWidth(); i++) {
            for (int j = 0; j < grid.getHeight(); j++) {
                // Get the state of the current tile
                Tile state = grid.getTileSafe(i, j).getState();

                // Set the color based on the tile's state
                switch (state.getRepresentation()) {
                    case "Forest" -> fill(0, 255, 0);  // RGB for green
                    case "Water" -> fill(0, 0, 255);   // RGB for blue
                    case "Ground" -> fill(255, 127, 0); // RGB for orange
                    default -> fill(255); // White for unknown states (default)
                }

                // Draw the rectangle at the calculated position
                rect(i * w, j * h, w, h);
            }
        }
    }


    @Override
    public void setup() {

        Tile forest = new Forest();
        Tile ground = new Ground();
        Tile water = new Water();

        Tiles.registerTileCandidate(forest, forest.getRepresentation());
        Tiles.registerTileCandidate(ground, ground.getRepresentation());
        Tiles.registerTileCandidate(water, water.getRepresentation());

        allExistingTiles = Tiles.allTiles();


        int n = 200;

        int maxTries = 1;

        int tries = 0;

        while (tries++ < maxTries) {
            System.out.println("Try: " + tries);
            try {

                grid = new TileGrid(n, n, allExistingTiles);

                long start = System.currentTimeMillis();

                WaveFunctionCollapse wfc = new WaveFunctionCollapse() {
                };
                wfc.init(grid);
                wfc.collapse();

                long end = System.currentTimeMillis();

                System.out.printf("It took %s ms after %s tries", (end - start), tries);
                return;
            } catch (Exception e) {

                e.printStackTrace();

            }
        }


    }

    @Override
    public void settings() {
        size(800, 800);
    }

}