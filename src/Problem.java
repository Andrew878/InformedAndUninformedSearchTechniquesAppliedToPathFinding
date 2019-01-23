/**
 *Manages key information of a problem and manages the map methods.
 */
public class Problem {
    
    private GridMap map;

    public Problem(GridMap map) {
        this.map = map;
    }
    
    public void initialise(){
        this.map.createAllStates();
        this.map.populateStateRelationships();
    }

    public GridMap getMap() {
        return this.map;
    }
    
    public void print(){
        System.out.println("this map has been recorded and initial states are:"+this.map.getInitialState().toString());
    }
    
}
