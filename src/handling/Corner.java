package handling;

public class Corner {

  private final String representation;

  /**
   * A Corner of a tile
   * @param representation helper for comparing two corners. This representation has to be done "clock-wise". Example in ReadME TODO: (WIP).
   */
  public Corner(String representation) {
    this.representation = representation;
  }

  /**
   *
   * @param cornerInQuestion the corner that requires a new neighbor
   * @param assumedTile the tile that is being checked to the corner
   * @return whether a tile can fit to the corner of another tile
   */
  public static boolean canTileFitCorner(Corner cornerInQuestion, Tile assumedTile) {
    return false;
  }

}
