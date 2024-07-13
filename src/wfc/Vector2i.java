package wfc;

public interface Vector2i extends Comparable<Vector2i> {

    int x();

    int y();

    String toString();

    Vector2i set (int x, int y);


    @Override
    default int compareTo(Vector2i o) {
        if (o.x() == this.x() && o.y() == this.y()) {
            return 0;
        }
        if ((o.x() * o.x() + o.y() * o.y()) > (this.x() * this.x() + this.y() * this.y())) {
            return 1;
        } else {
            return -1;
        }
    }
}
