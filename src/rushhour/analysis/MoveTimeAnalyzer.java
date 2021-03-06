package rushhour.analysis;

public class MoveTimeAnalyzer implements Analyzer {

    /**
     * Analyzes a log based on the avgerage time taken per move
     * @param log the log to analyze
     * @return the average time taken per move
     */
    public double analyze(Log log) {
        double time = new TimeAnalyzer().analyze(log);
        double numMoves = new MoveAnalyzer().analyze(log);
        return time/numMoves;
    }
	public String description() {
		return "average move time";
	}
}
