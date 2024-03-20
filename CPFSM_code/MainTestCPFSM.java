
import java.io.IOException;
import java.util.ArrayList;

/**
 * Example of how to use the CPFSM algorithm from the source code and output the result to a file.
 * The CPFSM algorithm can mine all periodic frequent subgraphs from the graph database.
 *
 * @author Cai Jiayu
 */
public class MainTestCPFSM {

    public static void main(String [] arg) throws IOException, ClassNotFoundException{

        // set the input and output file path
        String input = ".\\Compounds.txt";
        String output = ".\\output_Compounds.txt";
        
        // set the minimum support threshold
        double minSupport = 0.5;

        // set the periodic parameter
        int minPeriodicity = 1;
        int minAveragePeriodicity = 1;

        // The maximum number of edges for frequent subgraph patterns
        int maxNumberOfEdges = Integer.MAX_VALUE;

        // If true, single frequent vertices will be output
        boolean outputSingleFrequentVertices = false;

        // If true, a dot file will be output for visualization using GraphViz
        boolean outputDotFile = false;

        // Output the ids of graph containing each frequent subgraph
        boolean outputGraphIds = true;

        // store the runtime
        ArrayList<Long> Runtime = new ArrayList< >();

        ArrayList<Integer> PatternCount = new ArrayList< >();

        ArrayList<Double>Maxmemory  = new ArrayList< >();

        double [] maxavg = {4};
        int [] maxper = {30};

        for (int x : maxper) {
            Runtime.clear();
            PatternCount.clear();
            Maxmemory.clear();

            for(double y : maxavg) {

                int maxPeriodicity = x;
                double maxAveragePeriodicity = y;

                // Apply the algorithm
                AlgoCPFSM algo = new AlgoCPFSM();
                algo.runAlgorithm(input, output, minSupport, minPeriodicity, maxPeriodicity,
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
