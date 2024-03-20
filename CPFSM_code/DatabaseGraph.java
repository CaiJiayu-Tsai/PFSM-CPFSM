import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * This is an implementation of a graph, used by the CPFSM algorithm.
 * The implementation enumerates all edges of a graph by (graph id, edge) pair.
 *  <br/><br/>
 *
 * This implementation saves the result to a file
 *
 * @see AlgoCPFSM
 */

public class DatabaseGraph extends Graph {

    /**
     * enumeration of each edge in a graph
     */
    private Map<Edge, EdgeEnumeration> edgesEnumeration;

    public DatabaseGraph(int id, Map<Integer, Vertex> vMap) {
        super(id, vMap);
    }

    /**
     * builds enumeration of graph edges
     */
    public void buildEdgeEnumeration() {
        edgesEnumeration = new HashMap<Edge, EdgeEnumeration>();

        Set<Edge> allEdges = getAllEdges();

        for (Edge edge: allEdges) {
            edgesEnumeration.put(edge, new EdgeEnumeration(getId(), edge));
        }
    }

    public EdgeEnumeration getEdgeEnumeration(Edge edge) {
        return edgesEnumeration.get(edge);
    }
}
