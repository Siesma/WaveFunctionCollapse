package wfc;

public class WaveFunctionCollapse {

    private Grid grid;

    private int[][] entropyMap;

    public void init (Grid grid) {
        this.grid = grid;
        collapse();
    }

    private void collapse () {
        computeEntropyMap();
    }

    private void propagateConstraints () {

    }


    private void computeEntropyMap () {
        for(int i = 0; i < entropyMap.length; i++) {
            for(int j = 0; j < entropyMap[i].length; j++) {

            }
        }
    }

    private void updateEntropyMap() {

    }



}
