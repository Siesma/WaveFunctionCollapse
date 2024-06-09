package handling;

public class Corner {

  private final String representation;

  /**
   * A Corner of a tile
   *
   * @param representation helper for comparing two corners. This representation has to be done "clock-wise". Example in ReadME TODO: (WIP).
   */
  public Corner(String representation) {
    this.representation = representation;
  }

  /**
   * @param cornerInQuestion the corner that requires a new neighbor
   * @param assumedTile      the tile that is being checked to the corner
   * @return whether a tile can fit to the corner of another tile
   */
  public static boolean canTileFitCorner(Corner cornerInQuestion, Tile assumedTile) {
    for (int i = 0; i < assumedTile.getCorners().length; i++) {
      if (cornerInQuestion.getRepresentation().equalsIgnoreCase(rotateRepresentation(assumedTile, assumedTile.getCorners()[i].getRepresentation(), i + 2))) {
        return true;
      }
    }
    return false;
  }

  /**
   * @param origin         the original tile used for less hardcoded lengths
   * @param representation the representation that will be rotated
   * @param n              the number of 90° rotations the corner will do
   * @return a rotated representation of the original representation by n*90°
   */
  private static String rotateRepresentation(Tile origin, String representation, int n) {
    String temp = representation;
    for (int i = 0; i < /* could this be negative??, this will prevent it from being negative. */origin.getCorners().length + (n % origin.getCorners().length); i++) {
      temp = reverseString(temp);
    }
    return temp;
  }

  /**
   * @param input the text that has to be reversed
   * @return the reverse of the input
   */
  private static String reverseString(String input) {
    StringBuilder out = new StringBuilder();
    for (int i = input.length(); i > 0; i--) {
      out.append(input.charAt(i));
    }
    return out.toString();
  }

  /*
  ----------
  Getters and Setters
  ----------
   */
  public String getRepresentation() {
    return representation;
  }
}
