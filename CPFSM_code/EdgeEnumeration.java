import java.util.Objects;

/**
 * This is an implementation of database graph edge enumeration, used by the CPFSM algorithm.
 * Each edge in database graphs is uniquely enumerated by (graph id, edge) pair.
 *  <br/><br/>
 * This implementation saves the result to a file
 *
 * @see AlgoCPFSM
 */

public class EdgeEnumeration {
    /**
     * database graph id
     */
    private int gid;
    /**
     * database graph edge
     */
    private Edge edge;

    public EdgeEnumeration(int gid, Edge edge) {
        this.gid = gid;
        this.edge = edge;
    }

    public int getGid() {
        return gid;
    }

    public Edge getEdge() {
        return edge;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EdgeEnumeration that = (EdgeEnumeration) o;
        return gid == that.gid && edge.equals(that.edge);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gid, edge);
    }
}
