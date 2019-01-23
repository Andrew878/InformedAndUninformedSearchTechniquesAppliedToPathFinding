import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Informed Best First Search. Uses priority queue as data structure for
 * frontier. Method descriptions are in General Graph Search Algorithm super
 * class. The distMeasure String input let's determines whether Euclidean or
 * Chebyshev distance is used.
 */
public class BestFS extends GeneralGraphSearch {

    private PriorityQueue<Node> frontierSet;
    private Comparator<Node> heuristic;
    private String distMeasure;

    public BestFS(Problem problem, Comparator<Node> heuristic, String distMeasure) {
        super(problem, "Informed Best First - " + heuristic.toString() + " " + distMeasure);

        // we use the prioirty queue data structure given it is Best First Search
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
     * Method has no effect for BestFS. Is for replacing frontier nodes in A*
     *
     * @param oldNode
     * @param newNode
     */
    @Override
    Boolean isCheapestPathAndInFrontier(Node node) {
        return false;
    }

    /**
     * Method has no effect for BestFS. Is for replacing frontier nodes in A*
     *
     * @param oldNode
     * @param newNode
     */
    @Override
    void replaceNode(Node oldNode, Node newNode) {
    }

}
