/**
 *A record of a states X,Y location. Calculates Euclidean and Chebyshev distances.
 */
public class locationAttributes {
    
    private int x;
    private int y;
    private double distFromGoal;

    public locationAttributes(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Records Euclidean distance from goal.
     * @param goal location of goal
     */
    public void setDistFromGoal(locationAttributes goal) {
        this.distFromGoal = Math.sqrt(Math.pow((this.x - goal.getX()), 2) + Math.pow((this.y - goal.getY()), 2));
    }
    
    /**
     * Records Chebyshev distance from goal.
     * @param goal location of goal
     */
    public void setDistFromGoalCheb(locationAttributes goal) {
        this.distFromGoal = Math.max(Math.abs(this.x - goal.getX()), Math.abs((this.y - goal.getY())));
    }
    
    /**
     * Measures Euclidean distance between two points.
     * @param location1
     * @param location2
     * @return  Euclidean distance
     */
    public static double setDistFromInBetweenPoints(locationAttributes location1, locationAttributes location2) {
        return Math.sqrt(Math.pow((location1.getX() - location2.getX()), 2) + Math.pow((location1.getY() - location2.getY()), 2));
    }
    
    /**
     * Measures Chebyshev distance between two points.
     * @param location1
     * @param location2
     * @return  Chebyshev distance
     */
    public static double setDistFromInBetweenPointsCheb(locationAttributes location1, locationAttributes location2) {
        return Math.max(Math.abs(location1.getX() - location2.getX()), Math.abs((location1.getY() - location2.getY())));
    }

    public double getDistFromGoal() {
        return this.distFromGoal;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    @Override
    public String toString() {
        return "(X,Y)=("+this.x+", "+this.y+")";
    }
       
    
    
}
