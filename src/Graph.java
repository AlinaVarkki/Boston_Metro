import java.util.List;
import java.util.Map;

interface Graph<N, E> {

    List<E> getPath(N source, N destination);
    List<E> getPathDFS(N source, N destination);
    void addNode(N node);
    void addEdge(E node);
    public Map<N, List<E>> getAdjMap();
}
