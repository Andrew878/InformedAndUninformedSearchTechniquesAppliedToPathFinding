import java.util.Comparator;

/**
 *For greedy heuristics, does not take past information into account.
 */
public class GreedyDistanceHeuristic implements Comparator<Node> {
    
    @Override
    public int compare(Node node1, Node node2) {
        
        if(node1.getDistanceFromGoal()>node2.getDistanceFromGoal()){return 1;};
        if(node1.getDistanceFromGoal()<node2.getDistanceFromGoal()){return -1;};
        return 0;
    }
    
    @Override
    public String toString() {
        return "Greedy"; 
    }
}
