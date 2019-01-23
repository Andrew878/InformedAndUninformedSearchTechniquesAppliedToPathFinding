import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A record of a map, takes a list of edges as input, and creates the States.
 * Does not consider
 */
public class Map {

    protected int[][] edges;
    protected HashMap<Integer, State> allStates;
    protected State initialState;
    protected List<State> goalStates;
    protected String mapName;

    public Map(int[][] edges, String name) {
        this.edges = edges;
        this.allStates = new HashMap<Integer, State>();
        this.goalStates = new ArrayList<State>();
        this.mapName = name;
    }

    /**
     * Initialises all the States of a map using the array. Labels with characters.
     * We do this step because we need to create all the states first before we can 
     * record which states can interact we each other.
     */
    public void createAllStates() {
        char name = 'a';

        for (int row = 0; row < this.edges.length; row++) {
            State newState = new State(false, false, name, row, new locationAttributes(0, 0));
            
            // a hashmap is used (as opposed ot List) so that it is faster to 
            // look up a state for populateStateRelationships() method.
            this.allStates.put(row, newState);
            name++;
        }
    }

    /**
     * Populates all the States (e.g. are they goal, or start?). Also records all
     * possible actions in between states.
     */
    public void populateStateRelationships() {

        
        // cycle row by row through the map array
        for (int row = 0; row < this.edges.length; row++) {

            for (int col = 0; col < this.edges[row].length; col++) {

                // recording which states are linked to each other and add to 
                // actions.
                if (this.edges[row][col] == 5) {

                    this.allStates.get(row).addActionsAvailable(this.allStates.get(col), 1.0);
                    
                   // recorded if initial state.
                } else if (this.edges[row][col] == 2) {

                    this.allStates.get(row).setIsInitialState(true);
                    this.initialState = this.allStates.get(row);
                    
                    // recorded if goal state.
                } else if (this.edges[row][col] == 8) {

                    this.allStates.get(row).setIsGoalState(true);
                    this.goalStates.add(this.allStates.get(row));

                }
            }
        }
    }

    public void printStates() {
        for (int i = 0; i < this.allStates.size(); i++) {
            System.out.println(this.allStates.get(i).toString());
        }
    }

    public HashMap<Integer, State> getAllStates() {
        return this.allStates;
    }

    public List<State> getGoalStates() {
        return this.goalStates;
    }

    public State getInitialState() {
        return this.initialState;
    }

    public String getMapName() {
        return this.mapName;
    }

}
