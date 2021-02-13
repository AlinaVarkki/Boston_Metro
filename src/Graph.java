import java.util.List;
import java.util.Map;

interface Graph<N, E> {

    List<N> getPath(N source, N destination);
    List<N> reconstructPath(Map<N, E> childParentMap, N source, N destination);
    void addNode(N node);
    void addEdge(E node);
}
