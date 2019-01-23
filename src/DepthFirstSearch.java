import java.util.Stack;

/**
 * Uninformed Depth First Search. Uses stack as data structure for frontier.
 * Method descriptions are in General Graph Search Algorithm super class
 */
public class DepthFirstSearch extends GeneralGraphSearch {

    private Stack<Node> frontierSet;

    public DepthFirstSearch(Problem problem) {

        super(problem, "Uninformed Depth First Search");

        // Uses the stack data structure given it is Depth First Search
        this.frontierSet = new Stack<Node>();
    }

    @Override
    void addNodeToFrontier(Node... node) {
        for (int i = 0; i < node.length; i++) {
            this.frontierSet.push(node[i]);
        }
    }

    @Override
    Node chooseNextNodeFromFrontier() {
        return this.frontierSet.pop();
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
     * Method has no effect for DepthFS. Is for replacing frontier nodes in A*
     *
     * @param oldNode
     * @param newNode
     */
    @Override
    Boolean isCheapestPathAndInFrontier(Node node) {
        return false;
    }

    /**
     * Method has no effect for DepthFS. Is for replacing frontier nodes in A*
     *
     * @param oldNode
     * @param newNode
     */
    @Override
    void replaceNode(Node oldNode, Node newNode) {

    }

}
