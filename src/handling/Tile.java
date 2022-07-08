package handling;

public class Tile {


  private final Corner[] corners;

  /**
   * This is a very weird way of representing this, this is only the case because I am uncertain if this, in future,
   * will maybe be extended to support hexagonal or triangular base tiles.
   * @param a - the first corner of the tile
   * @param b - the second corner of the tile
   * @param c - the third corner of the tile
   * @param d - the fourth corner of the tile
   */
  public Tile (Corner a, Corner b, Corner c, Corner d) {
      this.corners = new Corner[4];
      this.corners['a' - 'a'] = a;
      this.corners['b' - 'a'] = b;
      this.corners['c' - 'a'] = c;
      this.corners['d' - 'a'] = d;
  }

  public Corner[] getCorners() {
    return corners;
  }
}
