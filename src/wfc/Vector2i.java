package wfc;

public record Vector2i(int x, int y) implements Comparable<Vector2i> {

    @Override
    public String toString() {
        return String.format("(%s, %s)", x, y);
    }

    @Override
    public int compareTo(Vector2i o) {
        if(o.x == this.x && o.y == this.y) {
            return 0;
        }
        if((o.x*o.x + o.y*o.y) > (this.x*this.x + this.y*this.y)) {
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    public boolean equals (Object o) {
        if(this == o) {
            return true;
        }
        if(o == null) {
            return false;
        }
        if(getClass() != o.getClass()) {
            return false;
        }
        Vector2i ovec = (Vector2i) o;
        if(this.compareTo(ovec) != 0) {
            return false;
        }
        return true;
    }

}
