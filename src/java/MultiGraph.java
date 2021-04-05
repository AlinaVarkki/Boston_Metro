import javafx.util.Pair;

import java.util.*;

public class MultiGraph<N, E extends Edge<N>> implements Graph<N, E> {

    /**
     * This class contains types N(node) and E(edge) that represent Stations and Connections using Generics
     * Adjacency map stores the Nodes and Edges
     */

    private Map<N, List<E>> adjacencyMap;

    public MultiGraph() {
        adjacencyMap = new HashMap<>();
    }

    @Override
    public Map<N, List<E>> getAdjMap() {
        return adjacencyMap;
    }

    @Override
    public void addNode(N node) {
        adjacencyMap.put(node, new ArrayList<>());
    }

    @Override
    public void addEdge(E edge) {
        List<E> node1Adj = adjacencyMap.get(edge.getNode1());
        if (node1Adj != null) node1Adj.add(edge);
    }

    @Override
    public List<E> getPath(N source, N destination) {
        List<String> visited = new ArrayList<>();
        Deque<Triple<N, List<E>, Integer>> deque = new LinkedList<>();

        deque.add(new Triple<>(source, new LinkedList<>(), 0));
        Triple<N, List<E>, Integer> currTriple;
        N currNode;
        List<E> currPath;
        Integer currTransitions;
        N neighbourNode;

        while (!deque.isEmpty()) {
            ArrayList<Triple<N, List<E>, Integer>> nodesToEnqueueSameLabel = new ArrayList<>();
            ArrayList<Triple<N, List<E>, Integer>> nodesToEnqueueDiffLabel = new ArrayList<>();

            currTriple = deque.poll();
            currNode = currTriple.first;
            currPath = currTriple.second;
            currTransitions = currTriple.third;

            if (currNode == destination) {
                List<E> bestPath = currPath;
                Triple<N, List<E>, Integer> queueTriple;
                N queueNode;
                List<E> queuePath;
                Integer queueTransitions;
                while (!deque.isEmpty()) {
                    queueTriple = deque.poll();
                    queueNode = queueTriple.first;
                    queuePath = queueTriple.second;
                    queueTransitions = queueTriple.third;
                    if (queueNode == destination && (queueTransitions < currTransitions && bestPath.size() == queuePath.size())) {
                        bestPath = queuePath;
                    }
                }
                return bestPath;
            }


            if (currPath.size() > 0) {
                String labelFrom = currPath.get(currPath.size() - 1).getLabel();
                visited.add(currNode.toString() + labelFrom);
            } else {
                visited.add(currNode.toString() + "any");
            }
            List<E> adjEdges = adjacencyMap.get(currNode);

            for (E edge : adjEdges) {
                neighbourNode = edge.getOppositeNode(currNode);
                String p1 = neighbourNode.toString() + edge.getLabel();
                String p2 = neighbourNode.toString() + "any";

                if (!visited.contains(p1) && !visited.contains(p2)) {
                    List<E> newPath = new LinkedList<>(currPath);
                    newPath.add(edge);
                    //always put in front the edges that have the same label so the line would get explored first
                    if (currPath.size() > 0 && edge.getLabel().equals(currPath.get(currPath.size() - 1).getLabel())) {
                        nodesToEnqueueSameLabel.add(new Triple<>(neighbourNode, newPath, currTransitions));
                    } else {
                        nodesToEnqueueDiffLabel.add(new Triple<>(neighbourNode, newPath, currTransitions + 1));
                    }
                }
            }
            deque.addAll(nodesToEnqueueSameLabel);
            deque.addAll(nodesToEnqueueDiffLabel);


        }
        System.out.println("No path found");
        return new LinkedList<E>();
    }

    /**
     * @param source,destination used as Start and Finish points for Path Construction
     *                           visited list keeps track of the line it has been visited from along with the node
     *                           Builds all possible paths from source to destination prioritizing exploring line that the last explored node is on
     *                           Calls removeCycle
     * @return LinkedList of Edges for the current Path
     */
    @Override
    public List<E> getPathDFS(N source, N destination) {
        List<String> visited = new ArrayList<>();
        Deque<Pair<N, List<E>>> deque = new LinkedList<>();

        deque.add(new Pair<>(source, new LinkedList<>()));
        Pair<N, List<E>> currPair;
        N currNode;
        List<E> currPath;
        N neighbourNode;

        while (!deque.isEmpty()) {
            currPair = deque.poll();
            currNode = currPair.getKey();
            currPath = currPair.getValue();

            if (currNode == destination) {
                deque.clear();
                return removeCycle(currPath);
            }


            if (currPath.size() > 0) {
                String labelFrom = currPath.get(currPath.size() - 1).getLabel();
                visited.add(currNode.toString() + labelFrom);
            } else {
                visited.add(currNode.toString() + "any");
            }
            List<E> adjEdges = adjacencyMap.get(currNode);
            for (E edge : adjEdges) {
                neighbourNode = edge.getOppositeNode(currNode);
                String p1 = neighbourNode.toString() + edge.getLabel();
                String p2 = neighbourNode.toString() + "any";
                if (!visited.contains(p1) && !visited.contains(p2)) {
                    List<E> newPath = new LinkedList<>(currPath);
                    newPath.add(edge);
                    //always put in front the edges that have the same label so the line would get explored first
                    if (currPath.size() > 0 && edge.getLabel().equals(currPath.get(currPath.size() - 1).getLabel())) {
                        deque.addFirst(new Pair<>(neighbourNode, newPath));
                    } else {
                        deque.add(new Pair<>(neighbourNode, newPath));
                    }
                }
            }

        }
        System.out.println("No path found");
        return new LinkedList<E>();
    }

    /**
     * @param path is a List of Edges
     *             Method works through list of edges not visited in specific path and removes cycles present
     * @return List
     */
    public List<E> removeCycle(List<E> path) {
        List<E> pathNoCycle = new ArrayList<>();
        List<String> edges = new ArrayList<>();
        for (E edge : path) {
            String currReversePath = edge.getNode2() + edge.getLabel() + edge.getNode1();
            if (!edges.contains(currReversePath)) {
                pathNoCycle.add(edge);
                edges.add(edge.getNode1() + edge.getLabel() + edge.getNode2());
            } else {
                pathNoCycle.remove(pathNoCycle.size() - 1);
            }
        }
        return pathNoCycle;
    }

}
