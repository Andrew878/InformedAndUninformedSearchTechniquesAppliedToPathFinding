import java.util.HashMap;
import java.util.Objects;

/**
 * A record of a particular 'State' which each node will be associated with.
 * Each state has some identity boolean variables, a list of next potential
 * moves, and some location attributes (which help calculate distances etc).
 */
public class State {

    private Boolean isInitialState;
    private Boolean isGoalState;
    private Boolean isOccupied;
    private char name;
    private int index;
    private HashMap<State, Double> nextMoves;
    private locationAttributes locationAttributes;

    public State(Boolean isInitialState, Boolean isGoalState, char name, int index, locationAttributes location) {
        this.isInitialState = isInitialState;
        this.isGoalState = isGoalState;
        this.isOccupied = false;
        this.name = name;
        this.index = index;
        this.nextMoves = new HashMap<State, Double>();
        this.locationAttributes = location;
    }

    public Boolean IsGoalState() {
        return this.isGoalState;
    }

    public Boolean IsInitialState() {
        return this.isInitialState;
    }

    public void setIsGoalState(Boolean isGoalState) {
        this.isGoalState = isGoalState;
    }

    public void setIsInitialState(Boolean isInitialState) {
        this.isInitialState = isInitialState;
    }

    public void setIsOccupied(Boolean isOccupied) {
        this.isOccupied = isOccupied;
    }

    public char getName() {
        return this.name;
    }

    /**
     * Returns record of available actions for a given state.
     *
     * @return a hashmap of actions
     */
    public HashMap<State, Double> getActionsAvailable() {
        return this.nextMoves;
    }

    /**
     * Adds a new potential action for state.
     *
     * @return a hashmap of actions
     */
    public void addActionsAvailable(State state, Double cost) {
        this.nextMoves.put(state, cost);
    }

    /**
     * Sets the path costs for a specific action from a state.
     */
    public void setActionCost(State state, Double newCost) {
        this.nextMoves.replace(state, newCost);
    }

    /**
     * Returns string of actions available.
     */
    public String stringOfActionsAvailable() {
        String temp = "{";
        for (State state : this.nextMoves.keySet()) {
            temp += "(State: " + state.name + ", Cost: " + this.nextMoves.get(state) + ") ";
        }
        return temp + "}";
    }

    /**
     * Returns location attributes of a state.
     */
    public locationAttributes getLocationAttributes() {
        return this.locationAttributes;
    }
  /**
     * Sets location attributes of a state.
     */
    public void setLocationAttributes(locationAttributes locationAttributes) {
        this.locationAttributes = locationAttributes;
    }

    /**
     * Returns string summary of a state.
     */
    @Override
    public String toString() {
        String temp = "State: " + this.name + ", Index: " + this.index + ", Initial: ";
        temp += this.isInitialState + ", Goal: " + this.isGoalState;

        if (!(this.getLocationAttributes() == null)) {
            temp += ", " + this.getLocationAttributes().toString();
        }
        temp += ", Actions Available: " + this.stringOfActionsAvailable();
        return temp;
    }

    /**
     * Hashing based on name and location.
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + this.name;
        hash = 29 * hash + this.index;
        return hash;
    }

    /**
     * Equality based on name and location.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final State other = (State) obj;
        if (this.name != other.name) {
            return false;
        }
        if (this.index != other.index) {
            return false;
        }
        return true;
    }

}
