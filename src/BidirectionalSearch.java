import java.util.ArrayList;
import java.util.HashMap;


/**
 * Bidirectional Search. This implementation uses two Breadth First Searches.
 *
 */
public class BidirectionalSearch {

    private BreadthFirstSearch forwardSearch;
    private BreadthFirstSearch backwardSearch;
    private Problem problem;

     public BidirectionalSearch(Problem problem) {
        this.problem = problem;
        this.forwardSearch = new BreadthFirstSearch(problem);
        this.backwardSearch = new BreadthFirstSearch(problem);
    }

    /**
     * Initialises the two individual Breadth First searches. One from the
     * initial point, the other from the goal.
     */
    public void initialise() {
        this.forwardSearch.initialise();
        this.backwardSearch.InverseInitialise();
    }

    /**
     * Runs the bidirectional search by making calls to two indep. BF searches.
     *
     * @return solution record (if found or not)
     */
    public SolutionRecord findSolution() {

        SolutionRecord record = null;
        int step = 0;
        int totalExpNodes = 0;

        // Continue searching until one of the frontiers is empty (i.e. no solutions exist).
        while (!(this.forwardSearch.isFrontierSetEmpty()) && !(this.backwardSearch.isFrontierSetEmpty())) {

            // take a single step forward in each direction
            System.out.println("###### NEW FORWARD STEP OF BFS ########");
            SolutionRecord recordF = this.forwardSearch.findSolutionSingleStep();
            System.out.println("###### NEW BACKWARD STEP OF BFS ########");
            SolutionRecord recordB = this.backwardSearch.findSolutionSingleStep();
            step++;

            // checking if solution is found by normal means (i.e. not and intersection)
            // and return the most appropriate one
            if ((recordF != null) || (recordB != null)) {
                this.returnBestSolution(recordB, recordF);
            }

            // renaming to make the code easier to read
            Node forwardNode = this.forwardSearch.getSelectedNode();
            Node backwardNode = this.backwardSearch.getSelectedNode();

            totalExpNodes = this.forwardSearch.getNodesExpandedCount() + this.backwardSearch.getNodesExpandedCount();
            
            String path = "\n   Forward:  " + forwardNode.pathSummary() + "\n   Backward: " + backwardNode.pathSummary();
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("OVERALL BIDIRECTIONAL SUMMARY: At step " + step + " , path is " + path);
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            
            // creates some combined cost and depth figures 
            int combinedDepth = forwardNode.getDepth() + backwardNode.getDepth();
            int combinedCost = (int) (forwardNode.getPathCostToHere() + backwardNode.getPathCostToHere());

            // tests if the backward node has entered into any explored forward states
            if (this.forwardSearch.getCheapestNodePerState().containsKey(backwardNode.getState())) {

                // if intersection has been found picks the least expensive node 
                // from explored forward states
                Node forwardSolutionNode = this.forwardSearch.getCheapestNodePerState().get(backwardNode.getState());
                Node backwardSolutionNode = backwardNode;

                int combinedCostForward = (int) (forwardSolutionNode.getPathCostToHere() + backwardSolutionNode.getPathCostToHere());
                combinedCost = combinedCostForward;

                // this additional check step ensures the forward node hasn't at 
                // the same time entered into any explored backward states which 
                // might be actually be cheaper.
                if (this.backwardSearch.getCheapestNodePerState().containsKey(forwardNode.getState())) {

                    // if intersection has been found picks the least expensive 
                    // node from explored backward states
                    Node intersectingBackwardNode = this.forwardSearch.getCheapestNodePerState().get(backwardNode.getState());
                    int combinedCostBackward = (int) (intersectingBackwardNode.getPathCostToHere() + forwardNode.getPathCostToHere());

                    // We need to check if the cost is cheaper, and then we need 
                    // to update our solution variables if appropriate
                    if (combinedCostBackward < combinedCostForward) {
                        forwardSolutionNode = forwardNode;
                        backwardSolutionNode = this.backwardSearch.getCheapestNodePerState().get(forwardNode.getState());
                        combinedCost = combinedCostBackward;
                    }

                }
                
                //create a combined path to store our results
                path = "[FWD: " + forwardSolutionNode.pathSummary() + "] meets at [BWD: " + backwardSolutionNode.pathSummary() + "]";
                String easyReadPath = "\n[FWD: " + forwardSolutionNode.pathSummary() + "] \n meets at \n[BWD: " + backwardSolutionNode.pathSummary() + "]";

                // make and return a solution record
                record = new SolutionRecord(true, this.problem.getMap(), totalExpNodes, "Uninformed Bidirectional", path, this.problem.getMap().getMapName(), combinedCost, 0, combinedCost);
                record.print();
                return record;

            }

        }
        
        // if not solution found, record a failed search attempt
        System.out.println("********Solution not found!!***************************");
        
        record = new SolutionRecord(false, this.problem.getMap(), totalExpNodes, "Uninformed Bidirectional", "n/a", this.problem.getMap().getMapName(), 0, 0, 0);
        record.print();

        return record;
    }

    /** Takes two solutions and returns one with lowest path cost.
     * 
     * @param recordB backward solution
     * @param recordF forward solution
     * @return cheapest solution
     */
    public SolutionRecord returnBestSolution(SolutionRecord recordB, SolutionRecord recordF) {

        // if both found in same turn, return the best solution
        if ((recordF != null) && (recordB != null)) {
            SolutionRecord betterSoln = recordB;
            if (recordB.getPathCost() > recordF.getPathCost()) {
                betterSoln = recordF;
            }
            return betterSoln;
        }

        //otherwise return the found solution;
        if (recordF != null) {
            return recordF;
        }
        if (recordB != null) {
            return recordB;
        }
        
        // Will never reach this far, but just to stop compliler warnings
        SolutionRecord rec = null;
        return rec;
    }
}