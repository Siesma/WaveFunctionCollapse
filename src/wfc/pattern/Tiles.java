package wfc.pattern;

import java.util.HashMap;
import java.util.HashSet;

public class Tiles {

    static HashMap<String, Tile> allTiles;

    static {
        allTiles = new HashMap<>();
    }

    public static void register (Tile tile, String representation) {
        System.out.println("Registering " + representation);
        if(allTiles.containsKey(representation)) {
            System.err.println("There already exists a tile with said representation");
            return;
        }

        allTiles.put(representation, tile);
    }

    public static Tile getTile (String representation) {
        return allTiles.get(representation);
    }

    public static HashSet<Tile> allTiles () {
        HashSet<Tile> tiles = new HashSet<>(allTiles.values());
        return tiles;
    }

}
