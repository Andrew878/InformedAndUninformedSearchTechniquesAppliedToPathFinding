/**Record of solution. Contains all key details of a successful or failed search
 *
 */
public class SolutionRecord {

    private Map map;
    private int nodesExpandedCount;
    private String nameOfSearch;
    private String path;
    private String mapName;
    private double pathCost;
    private int depth;
    private float effBranchFactor;
    private boolean solnFound;

    public SolutionRecord(boolean found, Map map, int nodesExpandedCount, String nameOfSearch, String Path, String mapName, double PathCost, float effBranchFactor, int depth) {
        this.map = map;
        this.nodesExpandedCount = nodesExpandedCount;
        this.nameOfSearch = nameOfSearch;
        this.path = Path;
        this.mapName = mapName;
        this.pathCost = PathCost;
        this.effBranchFactor = effBranchFactor;
        this.depth = depth;
        this.solnFound = found;
    }

    /**
     * Prints a search summary.
     */
    public void print() {

        if (this.solnFound) {
            System.out.println("");
            System.out.println(this.map.getMapName() + " solution found using: " + this.nameOfSearch);
            System.out.println("Solution path depth: " + this.depth);
            System.out.println("Solution path cost: " + this.pathCost);
            System.out.println("Number of expanded nodes: " + this.nodesExpandedCount);
            // System.out.println("Effective branching factor: " + this.effBranchFactor);
            System.out.println("Solution path: " + this.path);
            System.out.println("");
        } else {
            System.out.println("");
            System.out.println(this.map.getMapName() + " solution NOT found using: " + this.nameOfSearch);
            System.out.println("Solution path depth: N/A");
            System.out.println("Solution path cost: N/A");
            System.out.println("Number of expanded nodes: " + this.nodesExpandedCount);
            // System.out.println("Effective branching factor: N/A" );
            System.out.println("Solution path: N/A");
            System.out.println("");
        }

    }

    /**
     * Prints a search summary in a single line. Makes it easier to manipulate in excel.
     */
    public String singleLineStringSummary() {
        String summary = "";
        String delimiter = "£";
        
        summary += this.map.getMapName() + delimiter
                + this.nameOfSearch + delimiter
                + this.solnFound + delimiter
                + this.depth + delimiter
                + this.pathCost + delimiter
                + this.nodesExpandedCount + delimiter
                + this.path + delimiter
                + "\n";
        
        return summary;
    }

    public Map getMap() {
        return this.map;
    }

    public int getNodesExpandedCount() {
        return this.nodesExpandedCount;
    }

    public String getNameOfSearch() {
        return this.nameOfSearch;
    }

    public String getPath() {
        return this.path;
    }

    public String getMapName() {
        return this.mapName;
    }

    public double getPathCost() {
        return this.pathCost;
    }

    public int getDepth() {
        return this.depth;
    }

    public float getEffBranchFactor() {
        return this.effBranchFactor;
    }

    public boolean isSolnFound() {
        return this.solnFound;
    }

}
