import java.util.Comparator;

/**
 * Extension of Map with Grid Data
 *
 * @author al278
 */
public class GridMap extends Map {

    private int[][] gridLocation;
    private int maxX;
    private int maxY;

    public GridMap(int[][] edges, String name, int[][] gridLocation) {
        super(edges, name);
        this.gridLocation = gridLocation;
        this.maxX = this.getMax(0);
        this.maxY = this.getMax(1);
    }

    /**
     * Uses grid location to record the distances between states and the goal,
     * and also distances between potential actions.
     *
     * @param distMeasure chooses whether Euclidean or Chebyshev distance is
     * used.
     */
    public void populateLocations(String distMeasure) {

        // this determins what distance measure is used in the heauristic
        boolean SLD = false;
        boolean Cheb = false;
        if (distMeasure.contains("SLD")) {
            SLD = true;
        } else if (distMeasure.contains("Cheb")) {
            Cheb = true;
        } else {
            System.out.println("----FAIL----- please select valid heuristic");
        }

        locationAttributes goalLocation = new locationAttributes(0, 0);

        // populate each location for each state
        for (int i = 0; i < super.allStates.size(); i++) {
            locationAttributes location = new locationAttributes(this.gridLocation[i][0], this.gridLocation[i][1]);
            super.allStates.get(i).setLocationAttributes(location);

            // goal state, record the goal location
            if (super.allStates.get(i).IsGoalState()) {
                goalLocation = location;
                super.goalStates.get(0).setLocationAttributes(goalLocation);
            }
        }

        // populating the distance from the goal for each state. 
        // the boolean switches ensure it is SLD or Cheb Distance
        for (int i = 0; i < super.allStates.size(); i++) {
            if (SLD) {
                super.allStates.get(i).getLocationAttributes().setDistFromGoal(goalLocation);
            } else if (Cheb) {
                super.allStates.get(i).getLocationAttributes().setDistFromGoalCheb(goalLocation);
            }
        }

        // populating the distance from each potential action (i.e. dist from one node to another)
        for (int i = 0; i < super.allStates.size(); i++) {

            for (State state : super.allStates.get(i).getActionsAvailable().keySet()) {

                // create two locations
                locationAttributes locationOrigin = super.allStates.get(i).getLocationAttributes();
                locationAttributes locationDest = state.getLocationAttributes();

                // populate the distance between them
                state.setActionCost(state, locationAttributes.setDistFromInBetweenPoints(locationOrigin, locationDest));
                super.allStates.get(i).setActionCost(state, locationAttributes.setDistFromInBetweenPoints(locationOrigin, locationDest));
                    
            }
        }

    }

    /**
     * Helper function that helps print out the map.
     */
    public Boolean isStateAt(int x, int y) {

        for (int i = 0; i < this.gridLocation.length; i++) {
            if ((this.gridLocation[i][0] == x) && (this.gridLocation[i][1] == y)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Helper function that helps print out the map.
     */
    public int getMax(int index) {
        int max = 0;
        for (int i = 0; i < this.gridLocation.length; i++) {
            if (this.gridLocation[i][index] > max) {
                max = this.gridLocation[i][index];
            }
        }
        return max;
    }

    /**
     * Helper function that helps print out the map.
     */
    public int getMaxY() {
        return maxY;
    }

    /**
     * Prints out the map in char form.
     */
    public void printMap() {
        char name = 'a';

        for (int y = 0; y <= this.maxY; y++) {
            for (int x = 0; x <= this.maxX; x++) {
                if (this.isStateAt(x, y)) {
                    System.out.print(name);
                    name++;
                } else {
                    System.out.print("+");
                }
            }
            System.out.println("");
        }
    }

}
