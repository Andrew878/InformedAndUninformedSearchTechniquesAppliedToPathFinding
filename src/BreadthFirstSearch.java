import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Uninformed Breadth First Search. Uses queue as data structure for frontier.
 * Method descriptions are in General Graph Search Algorithm super class
 */
public class BreadthFirstSearch extends GeneralGraphSearch {

    private Queue<Node> frontierSet;

    public BreadthFirstSearch(Problem problem) {
        super(problem, "Uninformed Breadth First Search");

        // we use the Queue data structure given it is Breadth First Search
        this.frontierSet = new LinkedList<Node>();
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
     * Method has no effect for BreadthFS. Is for replacing frontier nodes in A*
     *
     * @param oldNode
     * @param newNode
     */
    @Override
    Boolean isCheapestPathAndInFrontier(Node node) {
        return false;
    }

    /**
     * Method has no effect for BreadthFS. Is for replacing frontier nodes in A*
     *
     * @param oldNode
     * @param newNode
     */
    @Override
    void replaceNode(Node oldNode, Node newNode) {

    }

}
