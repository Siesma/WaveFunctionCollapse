package wfc.pattern;

import wfc.Vector2i;

import java.util.*;

public class Tiles {

    static HashMap<String, Tile> allTiles;

    static HashMap<String, Vector2i> neighbouringCandidates;

    static {
        allTiles = new HashMap<>();
        neighbouringCandidates = new HashMap<>();
    }

    public static void initDefaultNeighbouringCandidates() {
        registerNeighbourCandidate("Up", 0, 1); // up
        registerNeighbourCandidate("Down", 0, -1); // down
        registerNeighbourCandidate("Left", -1, 0); // left
        registerNeighbourCandidate("Right", 1, 0); // right

        registerNeighbourCandidate("UpRight", 1, -1);  // upright
        registerNeighbourCandidate("UpLeft", -1, -1);  // upleft
        registerNeighbourCandidate("DownRight", 1, 1); // downright
        registerNeighbourCandidate("DownLeft", -1, 1); // downleft
    }

    public static void registerNeighbouringCandidate(String representation, Vector2i vec) {
        System.out.printf("Registering %s as a neighbouring candidate with coordinates %s.\n", representation, vec);
        if (neighbouringCandidates.containsKey(representation)) {
            System.err.println("There already exists a neighbouring candidate with said representation");
            return;
        }
        neighbouringCandidates.put(representation, vec);

    }

    public static void registerNeighbourCandidate(String representation, int x, int y) {
        registerNeighbouringCandidate(representation, new Vector2i(x, y));
    }

    public static void registerTileCandidate(Tile... tiles) {
        for(Tile t : tiles) {
            registerTileCandidate(t, t.getRepresentation());
        }
    }

    public static void initAdjacencyOfAllTiles () {
        for(Tile t : allTiles.values()) {
            t.initAdjacencies();
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
