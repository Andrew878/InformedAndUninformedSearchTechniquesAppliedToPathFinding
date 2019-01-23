import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import com.sun.org.apache.bcel.internal.generic.RET;

/**
 * General Graph Search Algorithm used for all searches. Its sub-classes are
 * identical with the exception of data-structures used and also a mechanism
 * that lets the measure of distance change (e.g. Euclidean or Chebyshev
 * distance).
 *
 */
public abstract class GeneralGraphSearch {

    protected Problem problem;
    private Collection<State> exploredStates;
    private State initialState;

    // A collection of goal states is used in case there are multiplie goals
    protected Collection<State> goalStates;
    protected HashMap<State, Node> cheapestNodePerState;
    private int nodesExpandedCount;
    private String nameOfSearch;
    private Node selectedNode;

    public GeneralGraphSearch(Problem problem, String name) {
        this.problem = problem;
        this.nameOfSearch = name;
        this.nodesExpandedCount = 0;
        this.exploredStates = new ArrayList<State>();
        this.cheapestNodePerState = new HashMap<State, Node>();
    }

    /**
     * Initialises the graph. Processes the maps (identifies the goal states,
     * initial states) Initialises the first node and adds it to the frontier
     */
    public void initialise() {
        this.problem.initialise();

        System.out.println("~~~~~~~~~~~~~~~~~~" + this.problem.getMap().mapName + ", Begin New " + this.nameOfSearch + " ~~~~~~~~~~~~~~~~~~~~~~~~");

        // Can only populate these variables after the problem has been initialised
        // and the map has been read.
        this.initialState = this.problem.getMap().getInitialState();
        this.goalStates = this.problem.getMap().getGoalStates();

        //initialising first node. parent is null, depth and cost is zero.
        Node chosenNode = new Node(this.initialState, null, 0, 0);

        this.addNodeToFrontier(chosenNode);
        this.selectedNode = chosenNode;
    }

    /**
     * Runs Graph Search and returns a solution record (if found or not).
     *
     * @return A record containing key info about the search (e.g. success, cost
     * etc)
     */
    public SolutionRecord findSolution() {
        SolutionRecord record = null;

        // continue while there are still nodes in the frontier set.
        while (!this.isFrontierSetEmpty()) {

            record = this.findSolutionSingleStep();

            // if record returned is non-null, then break loop and return solution
            if (record != null) {
                return record;
            }
        }

        System.out.println("********Solution not found!!***************************");
        System.out.println("");

        // record search if solution unsuccessful and return record
        record = new SolutionRecord(false, this.problem.getMap(), this.nodesExpandedCount, this.nameOfSearch, this.selectedNode.pathSummary(), this.problem.getMap().getMapName(), this.selectedNode.getPathCostToHere(), 0, this.selectedNode.getDepth());
        record.print();
        return record;
    }

    /**
     * Runs a SINGLE step of Graph Search and returns a solution record (if
     * found or not).
     *
     * @return A record containing key info about the search (e.g. success, cost
     * etc). If no solution found yet, but frontier still non-empty we return
     * null.
     */
    public SolutionRecord findSolutionSingleStep() {

        System.out.println("At Expansion " + this.nodesExpandedCount + ", at (" + this.selectedNode.nodeTitle() + "), the frontier is :" + this.stringOfFrontierSet());

        // Choose next node from frontier and record expanded node. 
        // This is an abstract method and changes with each search technique
        Node chosenNode = this.chooseNextNodeFromFrontier();
        System.out.println("NODE CHOSEN WAS: " + chosenNode.toString());
        this.nodesExpandedCount++;

        // Reference copied so that 'selectedNode' can be easily accessed for 
        // bidirectional search
        this.selectedNode = chosenNode;

        // Test for goal state
        if (chosenNode.isGoalState()) {

            System.out.println("");
            System.out.println("********Solution found!!***************************");

            // Store solution in a solution record and return it
            SolutionRecord record = new SolutionRecord(true, this.problem.getMap(), this.nodesExpandedCount, this.nameOfSearch, chosenNode.pathSummary(), this.problem.getMap().getMapName(), chosenNode.getPathCostToHere(), 0, chosenNode.getDepth());
            record.print();
            return record;
        }

        // After checking if a goal state, we now update the explored set and 
        // expand a new node
        this.exploredStates.add(chosenNode.getState());
        this.printExploredStates();
        this.expandNodes(chosenNode);
        System.out.println("--------end of step------------");

        // because no solution was found for this node, we return null
        return null;
    }

    /**
     * Takes a parent node, checks if explored or in frontier, and adds new
     * nodes to frontier. This method also keeps track of the cheapest path cost
     * for a given state. This will be used for any node replacement in A*
     * search
     *
     * @param parentNode the parent-node we wish to investigate
     */
    public void expandNodes(Node parentNode) {

        // Cycle through the potential actions of the parent node that will lead to
        // a different state
        for (State potentialNewState : parentNode.getState().getActionsAvailable().keySet()) {

            // ignore if the parent state = action state (i.e. no staying in location)
            if (potentialNewState.equals(parentNode.getState())) {
                continue;
            }

            // Create new node and record the state, parent and cost of getting there.
            Node newNode = new Node(potentialNewState, parentNode, parentNode.getState().getActionsAvailable().get(potentialNewState));

            // Check if this different state hasn't been explored & if new node is not already in the frontier
            if (!this.exploredStates.contains(potentialNewState) && !this.isNodeInFrontier(newNode)) {

                // Keeping a record of the cheapest Node for a given state
                this.cheapestNodePerState.put(potentialNewState, newNode);

                this.addNodeToFrontier(newNode);

                // if state is already in frontier, but new node is cheaper, then we replace
                // and update our cheapeast node hashmap
            } else if (this.isCheapestPathAndInFrontier(newNode)) {
                System.out.println("*** Cheaper node found. Replace: " + this.cheapestNodePerState.get(potentialNewState).toString() + "\nfrom frontier with " + newNode.toString());
                this.replaceNode(this.cheapestNodePerState.get(potentialNewState), newNode);
                this.cheapestNodePerState.replace(potentialNewState, newNode);

            }
        }
    }

    /**
     * Chooses next node from frontier according to the appropriate search
     * method. Essentially the successor function.
     *
     * @return the next node chosen.
     */
    abstract Node chooseNextNodeFromFrontier();

    /**
     * Tests if frontier empty.
     *
     * @return boolean value
     */
    public abstract Boolean isFrontierSetEmpty();

    /**
     * Tests if node in frontier.
     *
     * @return boolean value
     */
    abstract Boolean isNodeInFrontier(Node node);

    /**
     * Returns string of frontier (helper function).
     *
     * @return String value of frontier
     */
    public abstract String stringOfFrontierSet();

    /**
     * adds node to frontier.
     */
    abstract void addNodeToFrontier(Node... node);

    /**
     * Checks if a node can be replaced in frontier.
     *
     * @return boolean value
     */
    abstract Boolean isCheapestPathAndInFrontier(Node node);

    /**
     * Replaces old node with new node in frontier.
     */
    abstract void replaceNode(Node oldNode, Node newNode);

    /**
     * Helps print a short explored state summary.
     */
    public void printExploredStates() {
        System.out.print("Explored states after goal checking chosen node: ");
        for (State state : this.exploredStates) {
            System.out.print(state.getName() + " ");
        }
        System.out.println("");
    }

    /**
     * Bidirectional search only, exactly same as initialise method but reverses
     * initial and goal states. Processes the maps (identifies the goal states,
     * initial states). Initialises the first node and adds it to the frontier
     */
    public void InverseInitialise() {
        this.problem.initialise();

        // e
        this.initialState = this.problem.getMap().getInitialState();
        this.initialState.setIsGoalState(true);
        this.initialState.setIsInitialState(false);
        this.goalStates = this.problem.getMap().getGoalStates();

        State reverseGoal = null;
        for (State state : this.goalStates) {
            state.setIsGoalState(false);
            state.setIsInitialState(true);
            reverseGoal = state;
        }

        System.out.println("initial states " + this.initialState);

        Node chosenNode = new Node(reverseGoal, null, 0, 0);
        System.out.println("goal state? " + chosenNode.isGoalState());

        this.addNodeToFrontier(chosenNode);
        this.selectedNode = chosenNode;
    }

    /**
     * Bidirectional search only, returns a record of the cheapest path per
     * node.
     */
    public HashMap<State, Node> getCheapestNodePerState() {
        return this.cheapestNodePerState;
    }

    /**
     * Bidirectional search only, returns how many nodes have been expanded.
     */
    public int getNodesExpandedCount() {
        return this.nodesExpandedCount;
    }

    /**
     * Bidirectional search only, returns node currently being used.
     */
    public Node getSelectedNode() {
        return selectedNode;
    }

}
