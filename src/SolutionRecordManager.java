import java.util.ArrayList;
import java.util.List;

/**
 * A collection of all solutions. Has some helpful printing methods.
 */
public class SolutionRecordManager {

    private List<SolutionRecord> records;

    public SolutionRecordManager() {
        this.records = new ArrayList<SolutionRecord>();
    }

    public void addSolution(SolutionRecord givensolution) {
        this.records.add(givensolution);
    }

    /**
     * Prints a map into delimited form, so can be used in excel.
     */
    public void printGivenMapToCSV() {

        String summary = "";
        String delimiter = "£";

        String firstLine = "Map Name" + delimiter
                + "Search Technique" + delimiter
                + "Solution found? " + delimiter
                + "Solution depth" + delimiter
                + "Solution path cost" + delimiter
                + "Solution expanded nodes" + delimiter
                + "Solution path" + delimiter
                + "\n";
        
        summary += firstLine;

        for (SolutionRecord record : this.records) {
            summary += record.singleLineStringSummary();
        }
        
        System.out.println(summary);
    }

    public List<SolutionRecord> getRecords() {
        return this.records;
    }

}
