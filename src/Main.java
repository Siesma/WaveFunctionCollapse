import example.TileGrid;
import example.tiles.*;
import processing.core.PApplet;
import wfc.WaveFunctionCollapse;
import wfc.pattern.Tile;
import wfc.pattern.Tiles;

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

        float w = (float) width / grid.getWidth();  // Calculate width of each cell
        float h = (float) height / grid.getHeight(); // Calculate height of each cell

        for (int i = 0; i < grid.getWidth(); i++) {
            for (int j = 0; j < grid.getHeight(); j++) {
                // Get the state of the current tile
                Tile state = grid.getTileSafe(i, j).getState();

                // Set the color based on the tile's state
                switch (state.getRepresentation()) {
                    case "Forest" -> fill(0, 255, 0);  // RGB for green
                    case "DeepForest" -> fill(0, 128, 0);  // RGB for green
                    case "Water" -> fill(0, 0, 255);   // RGB for blue
                    case "DeepWater" -> fill(0, 0, 128);   // RGB for deepblue
                    case "Ground" -> fill(255, 127, 0); // RGB for orange
                    default -> fill(255); // White for unknown states (default)
                }

                // Draw the rectangle at the calculated position
                rect(i * w, j * h, w, h);
            }
        }
    }

    @Override
    public void mousePressed() {

        long start = System.currentTimeMillis();
        int tries = generate(0);
        long end = System.currentTimeMillis();

        System.out.printf("It took %s ms after %s tries\n", (end - start), tries+1);
    }


    @Override
    public void setup() {

        Tiles.registerTileCandidate(new Forest());
        Tiles.registerTileCandidate(new Ground());
        Tiles.registerTileCandidate(new Water());
        Tiles.registerTileCandidate(new DeepWater());
        Tiles.registerTileCandidate(new DeepForest());



        Tiles.initDefaultNeighbouringCandidates();

        allExistingTiles = Tiles.allTiles();

        long start = System.currentTimeMillis();
        int tries = generate(0);
        long end = System.currentTimeMillis();

        System.out.printf("It took %s ms after %s tries\n", (end - start), tries+1);

    }

    public int generate(int curTry) {
        if(curTry >= 10) {
            System.err.println("Could not find suitable wave state");
            return -1;
        }
        int n = 10;
        grid = new TileGrid(n, n, allExistingTiles);
        WaveFunctionCollapse wfc = new WaveFunctionCollapse() {
        };
        wfc.init(grid);

        boolean suc = wfc.collapse();
        if(!suc) {
            return generate(curTry + 1);
        }
        return curTry;
    }

    @Override
    public void settings() {
        size(1920, 1080);
    }

}