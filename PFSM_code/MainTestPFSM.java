import java.io.*;
import java.util.ArrayList;

/**
 * Example of how to use the PFSM algorithm from the source code and output the result to a file.
 * The PFSM algorithm can mine all periodic frequent subgraphs from the graph database.
 *
 * @author Cai Jiayu
 */
public class MainTestPFSM {

	public static void main(String[] arg) throws IOException, ClassNotFoundException {

		// set the input and output file path
		String input = ".\\Chemical.txt";
		String output = ".\\output_Chemical.txt";

		// The maximum number of edges for frequent subgraph patterns
		int maxNumberOfEdges = Integer.MAX_VALUE;

		// If true, single frequent vertices will be output
		boolean outputSingleFrequentVertices = false;

		// If true, a dot file will be output for visualization using GraphViz
		boolean outputDotFile = false;

		// If true, output the ids of graph containing each frequent subgraph
		boolean outputGraphIds = true;

		// store the runtime
		ArrayList<Long> Runtime = new ArrayList<>();
		// store the patternCount
		ArrayList<Integer> PatternCount = new ArrayList<>();
		// store the memory
		ArrayList<Double> Maxmemory = new ArrayList<>();

		// set the periodic parameter
		int minPeriodicity = 1;
		int minAveragePeriodicity = 1;
		double[] maxavg = {5};
		int[] maxper = {30};

		for (int x : maxper) {
			Runtime.clear();
			PatternCount.clear();
			Maxmemory.clear();

			for (double y : maxavg) {

				int maxPeriodicity = x;
				double maxAveragePeriodicity = y;

				// Apply the algorithm
				AlgoPFSM algo = new AlgoPFSM();
				algo.runAlgorithm(input, output, minPeriodicity, maxPeriodicity,
						minAveragePeriodicity, maxAveragePeriodicity, outputSingleFrequentVertices,
						outputDotFile, maxNumberOfEdges, outputGraphIds);

				long runtime = algo.runtime;
				Runtime.add(runtime);

				int patternCount = algo.patternCount;
				PatternCount.add(patternCount);

				double maxmemory = algo.maxmemory;
				Maxmemory.add(maxmemory);

				// Print statistics about the algorithm execution
				algo.printStats();
			}
		}

	}
}