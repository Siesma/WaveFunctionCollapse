package example;

import wfc.Vector2i;

public class Simple2DVector implements Vector2i {

    private int x, y;

    @Override
    public int x() {
        return x;
    }

    @Override
    public int y() {
        return y;
    }

    @Override
    public Vector2i set(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }
}
