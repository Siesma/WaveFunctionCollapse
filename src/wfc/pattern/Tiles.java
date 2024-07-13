package wfc.pattern;

import example.Simple2DVector;
import wfc.Vector2i;

import java.util.*;
import java.util.function.Supplier;

public class Tiles {

    static HashMap<String, Tile> allTiles;

    static HashMap<String, Vector2i> neighbouringCandidates;

    static {
        allTiles = new HashMap<>();
        neighbouringCandidates = new HashMap<>();
    }

    public static void initDefaultNeighbouringCandidates(Supplier<Vector2i> vector2iSupplier) {
        registerNeighbourCandidate("Up", vector2iSupplier.get().set(0, 1)); // up
        registerNeighbourCandidate("Down", vector2iSupplier.get().set(0, -1)); // down
        registerNeighbourCandidate("Left", vector2iSupplier.get().set(-1, 0)); // left
        registerNeighbourCandidate("Right", vector2iSupplier.get().set(1, 0)); // right

        registerNeighbourCandidate("UpRight", vector2iSupplier.get().set(1, -1));  // upright
        registerNeighbourCandidate("UpLeft", vector2iSupplier.get().set(-1, -1));  // upleft
        registerNeighbourCandidate("DownRight", vector2iSupplier.get().set(1, 1)); // downright
        registerNeighbourCandidate("DownLeft", vector2iSupplier.get().set(-1, 1)); // downleft
    }

    public static void registerNeighbouringCandidate(String representation, Vector2i vec) {
        System.out.printf("Registering %s as a neighbouring candidate with coordinates %s.\n", representation, vec);
        if (neighbouringCandidates.containsKey(representation)) {
            System.err.println("There already exists a neighbouring candidate with said representation");
            return;
        }
        neighbouringCandidates.put(representation, vec);

    }

    public static void registerNeighbourCandidate(String representation, Vector2i vec) {
        registerNeighbouringCandidate(representation, vec);
    }

    public static void registerTileCandidate(Tile... tiles) {
        for(Tile t : tiles) {
            registerTileCandidate(t, t.getRepresentation());
        }
    }

    public static void initAdjacencyOfAllTiles () {
        for(Tile t : allTiles.values()) {
            t.initAdjacency();
        }
    }

    public static void registerTileCandidate(Tile tile, String representation) {
        System.out.printf("Registering %s as a Tile candidate.\n", representation);
        if (allTiles.containsKey(representation)) {
            System.err.println("There already exists a tile with said representation");
            return;
        }

        allTiles.put(representation, tile);
    }

    public static Tile getTile(String representation) {
        return allTiles.get(representation);
    }

    public static HashSet<Tile> allTiles() {
        HashSet<Tile> tiles = new HashSet<>(allTiles.values());
        return tiles;
    }

    public static HashMap<String, Vector2i> getNeighbouringCandidates() {
        return neighbouringCandidates;
    }
}
