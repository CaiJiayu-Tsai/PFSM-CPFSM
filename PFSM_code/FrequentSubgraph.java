import java.util.Set;
import java.io.*;
import java.util.*;


/**
 * This is a frequent subgraph. It stores the (1) DFS code of the subgraph,
 * (2) the support of the subgraph, and (3) the set of graphs where the 
 * subgraph appears.
 * <br/><br/>
 * This implementation saves the result to a file
 * @see AlgoPFSM
 */
public class FrequentSubgraph implements Comparable<FrequentSubgraph>{
	
	/** dfs code */
    public DFSCode dfsCode;
    
    /** the ids of graphs where the subgraph appears */
    public Set<Integer> setOfGraphsIDs;
    
    /** the support of the subgraph */
    public int support;

	public int largestPeriodicity;

	public int smallestPeriodicity;

	public double averagePeriodicity;
    
    /**
     * Constructor
     * @param dfsCode a dfs code
     * @param setOfGraphsIDs the ids of graphs where the subgraph appears
     * @param support the support of the subgraph
     */
    public FrequentSubgraph(DFSCode dfsCode, Set<Integer> setOfGraphsIDs, int support){
    	this.dfsCode = dfsCode;
    	this.setOfGraphsIDs = setOfGraphsIDs;
    	this.support = support;
    }

    /**
     * Compare this subgraph with another subgraph
     * @param o another subgraph
     * @return 0 if equal, -1 if smaller, 1 if larger (in terms of support).
     */
    public int compareTo(FrequentSubgraph o) {
		if(o == this){
			return 0;
		}
		long compare =  this.support - o.support;
		if(compare > 0){
			return 1;
		}
		if(compare < 0){
			return -1;
		}
		return 0;
	}
	/*
	public void calculateperiodicity(FrequentSubgraph subgraph, List<Graph> graphDB) {

		int databaseSize = graphDB.size();

		Set<Integer> NewGraphIDs = subgraph.setOfGraphsIDs;
		List<Integer> listGraphIDs = new ArrayList<Integer>(NewGraphIDs);
		// store the set period set of a graph
		List<Integer> listperiodset = new ArrayList<Integer>();
		listperiodset.add(listGraphIDs.get(0)+1);
		for (int i = 1; i < listGraphIDs.size(); i++) {
			listperiodset.add(listGraphIDs.get(i)-listGraphIDs.get(i-1));
		}
		listperiodset.add(graphDB.size()-listGraphIDs.get(listGraphIDs.size()-1)-1);

		Integer maxlistperiodset = Collections.max(listperiodset);
		Integer minlistperiodset = Collections.min(listperiodset);
		double avglistperiodset = (double) databaseSize / ((double) NewGraphIDs.size()+1) ;

		subgraph.largestPeriodicity = maxlistperiodset;
		subgraph.smallestPeriodicity = minlistperiodset;
		subgraph.averagePeriodicity = avglistperiodset;
	}
	*/

}
