/**
 * Runs the main commands that reads the maps and calls upon the different search
 * methods.
 */
public class SearchAssignment {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        MapCollection allMaps = new MapCollection();
        SolutionRecordManager allSolutions = new SolutionRecordManager();
        allMaps.readAndStore();

        GreedyDistanceHeuristic greedy = new GreedyDistanceHeuristic();
        WholisticDistanceHeuristic wholistic = new WholisticDistanceHeuristic();

        // cycle through each map, applying the different algorithms
        for (GridMap map : allMaps.getMapList()) {

            Problem problem = new Problem(map);
            
            // Uninformed Search Methods
            BreadthFirstSearch BFS = new BreadthFirstSearch(problem);
            DepthFirstSearch DFS = new DepthFirstSearch(problem);
            BFS.initialise();
            allSolutions.addSolution(BFS.findSolution());
            DFS.initialise();
            allSolutions.addSolution(DFS.findSolution());

            // Informed Search Methods with Euclidean Distance
            BestFS BestFS = new BestFS(problem, greedy, "SLD");
            AStarSearch AStar = new AStarSearch(problem, wholistic, "SLD");
            BestFS.initialise();
            BestFS.initialiseDistances();
            allSolutions.addSolution(BestFS.findSolution());
            AStar.initialise();
            AStar.initialiseDistances();
            allSolutions.addSolution(AStar.findSolution());

            // Uninformed Search Methods Bidirectional Search
            BidirectionalSearch BiSearch = new BidirectionalSearch(problem);
            BiSearch.initialise();
            allSolutions.addSolution(BiSearch.findSolution());

            // Informed Search Methods with Chebyshev Distance
            BestFS BestFSCheb = new BestFS(problem, greedy, "Cheb");
            AStarSearch AStarCheb = new AStarSearch(problem, wholistic, "Cheb");
            BestFSCheb.initialise();
            BestFSCheb.initialiseDistances();
            allSolutions.addSolution(BestFSCheb.findSolution());
            AStarCheb.initialise();
            AStarCheb.initialiseDistances();
            allSolutions.addSolution(AStarCheb.findSolution());

        }

        // print solutions to excel friendly format
        // allSolutions.printGivenMapToCSV();

    }
}
