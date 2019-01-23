import java.util.Comparator;

/**
 *For wholistic heuristics like A*, takes past information into account.
 */
public class WholisticDistanceHeuristic implements Comparator<Node>{

    @Override
    public int compare(Node node1, Node node2) {
        
        if((node1.getDistanceFromGoal()+node1.getPathCostToHere())>(node2.getDistanceFromGoal()+node2.getPathCostToHere())){
            return 1;
        }
        if((node1.getDistanceFromGoal()+node1.getPathCostToHere())<(node2.getDistanceFromGoal()+node2.getPathCostToHere())){
            return -1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Wholistic"; 
    }

    
    
}
