import java.util.*;

/**
 * This is an implementation of a DFS code projections into a graphs database, used by the CPFSM algorithm.
 *  <br/><br/>
 *
 * This implementation saves the result to a file
 *
 * @see AlgoCPFSM
 */

public class Projected {
    /**
     * list of DFS code projections
     */
    private List<PDFS> projected = new LinkedList<PDFS>();
    /**
     * ids of graphs to which DFS code has projection
     */
    private Set<Integer> graphIds;

    public void addProjection(PDFS projection) {
        projected.add(projection);
    }

    /**
     * for each DFS code edge builds a set of all enumerated edges projected by that edge
     * @return list of sets of all enumerated edges projected by each edge in the DFS code
     */
    public List<Set<EdgeEnumeration>> buildKeys() {
        List<Set<EdgeEnumeration>> keys = new ArrayList<Set<EdgeEnumeration>>();

        if (projected.size() == 0) {
            return new ArrayList<Set<EdgeEnumeration>>();
        }

        int length = projected.get(0).getLength();

        for (int i = 0; i < length; i++) {
            Set<EdgeEnumeration> key = new HashSet<EdgeEnumeration>();
            keys.add(key);
        }

        for (PDFS projection: projected) {
            int i = length -1;
            while (projection != null) {
                EdgeEnumeration edgeEnumeration = projection.getEdgeEnumeration();
                Set<EdgeEnumeration> key = keys.get(i);
                key.add(edgeEnumeration);
                projection = projection.getPrevious();
                i--;
            }
        }

        return keys;
    }

    /**
     * builds a set of all enumerated edges projected by the last edge of the DFS code
     * @return set of all enumerated edges projected by the last edge of the DFS code
     */
    public Set<EdgeEnumeration> lastEdgeKey() {
        Set<EdgeEnumeration> key = new HashSet<EdgeEnumeration>();

        if (projected.size() == 0) {
            return key;
        }

        for (PDFS projection: projected) {
            EdgeEnumeration edgeEnumeration = projection.getEdgeEnumeration();
            key.add(edgeEnumeration);
        }

        return key;
    }


    /**
     * converts linked list structure to java list structure
     * @return java list structure of projections
     */
    public Map<Integer, List<List<EdgeEnumeration>>> buildProjectionsInDatabaseGraph() {
        Map<Integer, List<List<EdgeEnumeration>>> graphProjections = new HashMap<Integer, List<List<EdgeEnumeration>>>();

        for (PDFS projection: projected) {
            Integer gid = projection.getEdgeEnumeration().getGid();

            if (!graphProjections.containsKey(gid)) {
                graphProjections.put(gid, new LinkedList<List<EdgeEnumeration>>());
            }

            List<EdgeEnumeration> projectionList = new LinkedList<EdgeEnumeration>();
            while (projection != null) {
                EdgeEnumeration edgeEnumeration = projection.getEdgeEnumeration();
                projectionList.add(0, edgeEnumeration);
                projection = projection.getPrevious();
            }
            graphProjections.get(gid).add(projectionList);
        }

        return graphProjections;
    }

    public Set<Integer> getGraphIds() {
        return graphIds;
    }

    public void setGraphIds(Set<Integer> graphIds) {
        this.graphIds = graphIds;
    }

    public List<PDFS> getProjected() {
        return projected;
    }

    public void setProjected(List<PDFS> projected) {
        this.projected = projected;
    }

    /**
     * check if projections have equivalent occurrence with their extension
     * @param extendedProjected projections of the extension of this projections
     * @return true if equivalent occurrence found, false otherwise
     */
    public boolean hasEquivalentOccurrence(Projected extendedProjected) {
        if (!graphIds.equals(extendedProjected.getGraphIds())) {
            return false;
        }

        if (projected.size() > extendedProjected.getProjected().size()) {
            return false;
        }

        Set<PDFS> extendedPDFSPrevious = new HashSet<PDFS>();

        // build set of projections without the extension
        for (PDFS extendedPdfs: extendedProjected.getProjected()) {
            extendedPDFSPrevious.add(extendedPdfs.getPrevious());
        }

        // check that every projection is equal to one of the extended projections after the extension is removed
        for (PDFS pdfs: projected) {
            if (!extendedPDFSPrevious.contains(pdfs)) {
                return false;
            }
        }

        return true;
    }

    public List<Integer> projectionsGraphIds() {
        List<Integer> graphIds = new ArrayList<Integer>();
        for (PDFS pdfs: projected) {
            graphIds.add(pdfs.getEdgeEnumeration().getGid());
        }

        return graphIds;
    }

    /**
     *
     * @param label counted label
     * @param graphDatabase graphs database
     * @return count of distinct vertices in all projections labeled by 'label' parameter
     */
    public int verticesWithLabelCount(int label, List<DatabaseGraph> graphDatabase) {
        Map<Integer, Set<Integer>> graphVerticesWithLabel = new HashMap<Integer, Set<Integer>>();
        List<Integer> graphIds = projectionsGraphIds();
        for (Integer graphId: graphIds) {
            graphVerticesWithLabel.put(graphId, new HashSet<Integer>());
        }

        for (PDFS pdfs: projected) {
            PDFS pdfsAt = pdfs;
            while (pdfsAt != null) {
                int gid = pdfsAt.getEdgeEnumeration().getGid();
                DatabaseGraph g = graphDatabase.get(gid);
                Edge edge = pdfsAt.getEdgeEnumeration().getEdge();
                int label1 = g.getVLabel(edge.v1);
                if (label1 == label) {
                    graphVerticesWithLabel.get(gid).add(edge.v1);
                }
                int label2 = g.getVLabel(edge.v2);
                if (label2 == label) {
                    graphVerticesWithLabel.get(gid).add(edge.v2);
                }

                pdfsAt = pdfsAt.getPrevious();
            }
        }

        int count = 0;

        for (int gid: graphVerticesWithLabel.keySet()) {
            count += graphVerticesWithLabel.get(gid).size();
        }

        return count;
    }
}
