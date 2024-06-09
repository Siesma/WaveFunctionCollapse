package handling;

import processing.core.PApplet;
import processing.core.PImage;

public class Tile {


  private Corner[] corners;

  private boolean populated = false;

  private int weight;

  private PImage renderImage;

  /**
   * @param weight the number of all the tiles possible to connect to this
   */
  public Tile(int weight) {
    this.corners = new Corner[4];
    this.weight = weight;
  }

  public Corner[] getCorners() {
    return corners;
  }


  /**
   * Renders this tile to the screen
   *
   * @param parent the applet in which it has to be rendered
   * @param w      the width of this tile
   * @param h      the height of this tile
   */
  public void render(PApplet parent, int w, int h) {
    if (!populated || renderImage == null) {
      return;
    }
    parent.image(renderImage, 0, 0, w, h);
  }

  /**
   * populates a given tile
   */
  public void populate(PImage image, Corner a, Corner b, Corner c, Corner d) {

    this.corners[0] = a;
    this.corners[1] = b;
    this.corners[2] = c;
    this.corners[3] = d;
    this.populated = true;
    this.renderImage = image;
    this.weight = calculateNewWeight();
  }

  public int calculateNewWeight () {
    return 0;
  }

  public int getWeight() {
    return weight;
  }
}
