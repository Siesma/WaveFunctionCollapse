import handling.Tile;
import processing.core.PApplet;

public class Main extends PApplet {


  private Tile[][] grid;

  private int CELL_ROW = 5;
  private int CELL_COL = 5;

  private int weight = 5;

  public static void main(String[] args) {
    PApplet.main("Main", args);
  }


  @Override
  public void draw() {

    background(0);
    int w = width / CELL_ROW;
    int h = height / CELL_COL;
    for (int i = 0; i < CELL_ROW; i++) {
      for (int j = 0; j < CELL_COL; j++) {
        push();
        translate(i * w, j * h);
        grid[i][j].render(this, w, h);
        pop();
      }
    }

    noLoop();
  }

  @Override
  public void setup() {
    this.grid = new Tile[CELL_ROW][CELL_COL];
    for (int i = 0; i < CELL_ROW; i++) {
      for (int j = 0; j < CELL_COL; j++) {
        grid[i][j] = new Tile(2);
      }
    }
  }

  @Override
  public void settings() {
    size(800, 800);
  }


  private void populateNextCell() {
    int[] weights = new int[weight];
    for(int i = 0; i < weight; i++) {
      weights[i] = 0;
    }
    for (int i = 0; i < CELL_ROW; i++) {
      for (int j = 0; j < CELL_COL; j++) {
        weights[grid[i][j].getWeight()]++;
      }
    }
    
    for(int i = weight; i > 0; i--) {
      if(weights[i] > 0) {
        break;
      }
    }

  }

}
