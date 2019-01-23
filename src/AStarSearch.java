import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Informed A* Search. Uses priority queue as data structure for frontier.
 * Method descriptions are in General Graph Search Algorithm super class. The
 * distMeasure String input let's determines whether Euclidean or Chebyshev
 * distance is used.
 */
public class AStarSearch extends GeneralGraphSearch {

    private PriorityQueue<Node> frontierSet;
    private Comparator<Node> heuristic;
    private String distMeasure;

    public AStarSearch(Problem problem, Comparator<Node> heuristic, String distMeasure) {
        super(problem, "Informed A* Search - " + heuristic.toString() + " " + distMeasure);

        // we use the priority queue data structure given A* Search
        this.heuristic = heuristic;
        this.distMeasure = distMeasure;
        this.frontierSet = new PriorityQueue<Node>(this.heuristic);
    }

    /**
     * An extra step for informed searches that populates the distance
     * attributes between states.
     */
    public void initialiseDistances() {
        super.problem.getMap().populateLocations(this.distMeasure);
    }

    @Override
    void addNodeToFrontier(Node... node) {
        for (int i = 0; i < node.length; i++) {
            this.frontierSet.add(node[i]);
        }
    }

    @Override
    Node chooseNextNodeFromFrontier() {
        return this.frontierSet.remove();
    }

    @Override
    public Boolean isFrontierSetEmpty() {
        return this.frontierSet.isEmpty();
    }

    @Override
    Boolean isNodeInFrontier(Node node) {
        return this.frontierSet.contains(node);
    }

    @Override
    public String stringOfFrontierSet() {
        return this.frontierSet.toString();
    }

    /**
     * Checks if a node can replace a more expensive node already in the
     * frontier.
     *
     * @param newNode node in question
     * @return boolean
     */
    @Override
    Boolean isCheapestPathAndInFrontier(Node newNode) {

        // To ensure that it doesn't look for the path cost to the initial state 
        // (which is always zero)
        if (newNode.getState().IsInitialState()) {
            return false;
        }
        
        double recordedCost = super.cheapestNodePerState.get(newNode.getState()).getPathCostToHere();

        // Testing if Node is cheaper AND already is in the frontier
        if ((newNode.getPathCostToHere() < recordedCost) && (this.frontierNodeGivenState(newNode.getState()) != null)) {
            return true;
        }
        return false;
    }

    /**
     * Checks if frontier holds a node with given state.
     *
     * @return node if found, null otherwise
     */
    private Node frontierNodeGivenState(State state) {
        for (Node node : this.frontierSet) {
            if (node.getState().equals(state)) {
                return node;
            }
        }
        return null;
    }

    /**
     * Replaces a node in frontier with new node .
     */
    @Override
    void replaceNode(Node oldNode, Node newNode) {
        this.frontierSet.remove(oldNode);
        this.frontierSet.add(newNode);
    }

}
