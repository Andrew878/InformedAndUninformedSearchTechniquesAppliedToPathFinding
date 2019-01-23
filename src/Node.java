import java.text.DecimalFormat;
import java.util.Objects;

/**
 * A record of a node. Contains information such as its State, depth and path
 * cost and parent.
 *
 */
public class Node {

    private State state;
    private Node parent;
    private double pathCostToHere;
    private int depth;
    private DecimalFormat decFormat;

    public Node(State state, Node parent, double incrementalCost, int depth) {
        this.state = state;
        this.parent = parent;
        this.pathCostToHere = incrementalCost;
        this.depth = depth;
        this.decFormat = new DecimalFormat("0.000");
    }

    public Node(State state, Node parent, double incrementalCost) {
        this.state = state;
        this.parent = parent;
        this.pathCostToHere = incrementalCost + this.parent.getPathCostToHere();
        this.depth = this.parent.getDepth() + 1;
        this.decFormat = new DecimalFormat("0.000");
    }

    public Node getParent() {
        return this.parent;
    }

    public State getState() {
        return this.state;
    }

    public int getDepth() {
        return this.depth;
    }

    public double getPathCostToHere() {
        return this.pathCostToHere;
    }

    public double getDistanceFromGoal() {
        return this.state.getLocationAttributes().getDistFromGoal();
    }

    public Boolean isGoalState() {
        return this.state.IsGoalState();
    }

    /**
     * A string summary of node with all detail.
     *
     * @return summary
     */
    public String toDetailString() {
        if (this.getParent() == null) {
            return "(Path begins " + this.getState().getName() + ", depth: " + this.getDepth()
                    + ", cost: " + this.getPathCostToHere() + ")";
        }
        return "(" + this.getParent().getState().getName() + " to " + this.getState().getName()
                + ", location " + (this.getState().getLocationAttributes().toString())
                + ", depth: " + this.getDepth() + ", g(n): " + this.decFormat.format(this.getPathCostToHere())
                + ", h(n): " + this.decFormat.format(this.getState().getLocationAttributes().getDistFromGoal())
                + ", f(n): " + this.decFormat.format(this.getPathCostToHere() + this.getState().getLocationAttributes().getDistFromGoal()) + ")";
    }

    /**
     * A shorted, easier to read string summary of a node.
     *
     * @return summary
     */
    @Override
    public String toString() {
        if (this.getParent() == null) {
            return "(Path begins " + this.getState().getName() + ", depth: " + this.getDepth()
                    + ", cost: " + this.getPathCostToHere() + ")";
        }
        return "\n(" + this.getParent().getState().getName() + " to " + this.getState().getName()
                + ", depth: " + this.getDepth() + ", g(n): " + this.decFormat.format(this.getPathCostToHere())
                + ", h(n): " + this.decFormat.format(this.getState().getLocationAttributes().getDistFromGoal())
                + ", f(n): " + this.decFormat.format(this.getPathCostToHere() + this.getState().getLocationAttributes().getDistFromGoal()) + ")";
    }

    public String nodeTitle() {
        if (this.getParent() == null) {
            return "Path begins " + this.getState().getName();
        }
        return this.getParent().getState().getName() + " to " + this.getState().getName();
    }
    
    
    /**
     * Recursively provides path summary of node.
     *
     * @return summary
     */
    public String pathSummary() {
        if (this.getParent() == null) {
            return this.toDetailString();
        }
        return this.getParent().pathSummary() + this.toDetailString();
    }

    /**
     * Tests equality of node based on State only.
     *
     * @param obj
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Node other = (Node) obj;
        if (!Objects.equals(this.state, other.state)) {
            return false;
        }
        return true;
    }

    /**
     * Uses state only.
     *
     * @return summary
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.state);
        return hash;
    }

}
