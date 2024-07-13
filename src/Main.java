import wfc.Simple2DVector;
import example.TileCell;
import example.TileGrid;
import example.tiles.*;
import processing.core.PApplet;
import wfc.Triplet;
import wfc.Vector2i;
import wfc.WaveFunctionCollapse;
import wfc.pattern.Tile;
import wfc.pattern.Tiles;

import java.util.function.Supplier;

public class Main extends PApplet {

    private TileGrid grid;

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
        generate();
    }


    @Override
    public void setup() {
        Tiles.initDefaultNeighbouringCandidates(new Supplier<Vector2i>() {
            @Override
            public Vector2i get() {
                return new Simple2DVector();
            }
        });

        Tiles.registerTileCandidate(
            new Forest(),
            new Ground(),
            new Water(),
            new DeepWater(),
            new DeepForest()
        );

        Tiles.initAdjacencyOfAllTiles();

        generate();
    }

    void generate() {
        long start = System.currentTimeMillis();
        int tries = generate(0);
        long end = System.currentTimeMillis();

        System.out.printf("It took %s ms after %s tries\n", (end - start), tries + 1);
    }

    public int generate(int curTry) {
        if (curTry >= 100) {
            System.err.println("Could not find suitable wave state");
            return -1;
        }
        int n = 100;
        grid = new TileGrid(Tiles.allTiles(), new Supplier<TileCell[][]>() {
            @Override
            public TileCell[][] get() {
                return new TileCell[n][n];
            }
        });
        WaveFunctionCollapse wfc = new WaveFunctionCollapse() {
        };
        wfc.init(grid);

        wfc.setFixedStates(
            new Triplet(0, 0, Tiles.getTile("DeepForest")),
            new Triplet(1, 0, Tiles.getTile("Forest")),
            new Triplet(2, 0, Tiles.getTile("Forest")),
            new Triplet(3, 0, Tiles.getTile("Forest")),
            new Triplet(4, 0, Tiles.getTile("Forest")),
            new Triplet(5, 0, Tiles.getTile("Forest")),
            new Triplet(6, 0, Tiles.getTile("Forest"))
        );

        boolean suc = wfc.collapse();
        if (!suc) {
            return generate(curTry + 1);
        }
        return curTry;
    }

    @Override
    public void settings() {
        size(1920, 1080);
    }

}