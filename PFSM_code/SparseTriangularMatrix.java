
import java.util.*;
import java.util.Map.Entry;


/**
 * This class is for creating a triangular matrix of integers by using HashMaps (a sparse matrix rather than a full matrix).
 * See the TriangularMatrixArray class for more details about what is a triangular matrix.
 * <br/><br/>
 * This implementation saves the result to a file
 * @see AlgoPFSM
 */
public class SparseTriangularMatrix {
	
	// the triangular matrix is a hashmap of hashmaps
	// where the key is an item I, then the value is a map where each entry is a key representing an item J
	// and a value representing the count of {I, J}.
	private HashMap<Integer, Map<Integer, List<Integer>>> matrix = new HashMap<Integer, Map<Integer, List<Integer>>>();

	/**
	 * Constructor of a new triangular matrix.
	 */
	public SparseTriangularMatrix(){

	}
	
	/**
	 * This constructor is for compatibility with the other TriangularMatrix implementation.
	 * The parameter "itemCount" is not used.
	 * @param itemCount
	 */
	public SparseTriangularMatrix(int itemCount){

	}
	
	/* (non-Javadoc)
	 * @see ca.pfv.spmf.datastructures.triangularmatrix.AbstractTriangularMatrix#toString()
	 */
	public String toString() {
		// create a string buffer
		StringBuilder temp = new StringBuilder();
		// for each row
		for (int i = 0; i < matrix.keySet().size(); i++) {
			temp.append(i);
			temp.append(": ");
			// for each column
			for (int j = 0; j < matrix.get(i).size(); j++) {
				temp.append(matrix.get(i).get(j)); // add the value at position i,j
				temp.append(" ");
			}
			temp.append("\n");
		}
		return temp.toString();
	}

	/* (non-Javadoc)
	 * @see ca.pfv.spmf.datastructures.triangularmatrix.AbstractTriangularMatrix#incrementCount(int, int)
	 */
	public void incrementCount(int i, int j, int id) {
		if(i < j) {
			// First get the map of i
			Map<Integer, List<Integer>> mapCount = matrix.get(i);
			if(mapCount == null) {
				mapCount = new HashMap<Integer, List<Integer>>();
				matrix.put(i, mapCount);
				List<Integer> list = new ArrayList<>();
				list.add(id);
				mapCount.put(j, list);
			}else {
				// Second, get the count of i,j
				if(mapCount.get(j) == null) {
					List<Integer> list = new ArrayList<>();
					list.add(id);
					mapCount.put(j, list);
				}else {
					List<Integer> list = mapCount.get(j);
					list.add(id);
					mapCount.put(j,list);
				}
			}
		}else {
			// First get the map of j
			Map<Integer, List<Integer>> mapCount = matrix.get(j);
			if(mapCount == null) {
				mapCount = new HashMap<Integer,List<Integer>>();
				matrix.put(j, mapCount);
				List<Integer> list = new ArrayList<>();
				list.add(id);
				mapCount.put(i, list);
			}else {
				// Second, get the count of i,j
				if(mapCount.get(i) == null) {
					List<Integer> list = new ArrayList<>();
					list.add(id);
					mapCount.put(i, list);
				}else {
					List<Integer> list = mapCount.get(i);
					list.add(id);
					mapCount.put(i, list);
				}
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see ca.pfv.spmf.datastructures.triangularmatrix.AbstractTriangularMatrix#getSupportForItems(int, int)
	 */
	public int getSupportForItems(int i, int j){
		if(i < j) {
			// First get the map of i
			Map<Integer, List<Integer>> mapCount = matrix.get(i);
			if(mapCount == null) {
				return 0;
			}else {
				// Second, get the count of i,j
				if(mapCount.get(j) == null) {
					return 0;
				}else {
					Integer count = mapCount.get(j).size();
					return count;
				}
			}
		}else {
			// First get the map of i
			Map<Integer, List<Integer>> mapCount = matrix.get(j);
			if(mapCount == null) {
				return 0;
			}else {
				// Second, get the count of i,j
				if(mapCount.get(i) == null) {
					return 0;
				}else {
					Integer count = mapCount.get(i).size();
					return count;
				}
			}
		}
	}

	public int getMaxperForItems(int i, int j, int size){
		if(i < j) {
			// First get the map of i
			Map<Integer, List<Integer>> mapCount = matrix.get(i);
			if(mapCount == null) {
				return 0;
			}else {
				// Second, get the count of i,j
				if(mapCount.get(j) == null) {
					return 0;
				}else {
					List<Integer> list = mapCount.get(j);
					List<Integer> periodlist = getperiodset(list,size);
					int maxcount =  Collections.max(periodlist);
					return maxcount;
				}
			}
		}else {
			// First get the map of j
			Map<Integer, List<Integer>> mapCount = matrix.get(j);
			if(mapCount == null) {
				return 0;
			}else {
				// Second, get the count of i,j
				if(mapCount.get(i) == null) {
					return 0;
				}else {
					List<Integer> list = mapCount.get(i);
					List<Integer> periodlist = getperiodset(list,size);
					int maxcount =  Collections.max(periodlist);
					return maxcount;
				}
			}
		}
	}


	public List<Integer> getperiodset(List<Integer> list, int size) {

		Collections.sort(list);
		// store the set period set of a graph
		List<Integer> periodset = new ArrayList<Integer>();
		periodset.add(list.get(0) + 1);
		for (int i = 1; i < list.size(); i++) {
			periodset.add(list.get(i) - list.get(i - 1));
		}
		periodset.add(size - list.get(list.size() - 1));

		return periodset;
	}



		public void removeInfrequentEntriesFromMatrix(int minsup) {
		for(Entry<Integer, Map<Integer, List<Integer>>> entry : matrix.entrySet()){
			
			Iterator<Entry<Integer,List<Integer>>> iter = (Iterator<Entry<Integer, List<Integer>>>) entry.getValue().entrySet().iterator();
			while (iter.hasNext()) {
				Entry<Integer, List<Integer>> entry2 = (Entry<Integer, List<Integer>>) iter
						.next();
				if( entry2.getValue() == null) {
					iter.remove();
				} else if (entry2.getValue().size() < minsup){
					iter.remove();
				}
			}
		}
	}
}
