import java.util.*;

public class MultiGraph<N, E extends Edge<N>> implements Graph<N,E> {
    //This class contains types N(node) and E(edge) that represent stations and edges using generics

    //map that stores the nodes and edges adjacent to it
    private Map<N, List<E>> adjacencyMap;

    public MultiGraph() {
        adjacencyMap = new HashMap<>();
    }


    @Override
    public void addNode(N node){
        adjacencyMap.put(node, new ArrayList<>());
    }

    @Override
    public void addEdge(E edge){
        List<E> node1Adj = adjacencyMap.get(edge.getNode1());
        List<E> node2Adj = adjacencyMap.get(edge.getNode2());
        if(node1Adj != null) node1Adj.add(edge);
        if(node2Adj != null) node2Adj.add(edge);
    }

    public List<E> getPathTuple(N source, N destination) {
        List<N> visited = new ArrayList<>();
        //contains a Node and edge that led to it(need to map nodes to the edge, not node, to see label of the node that led to it and prioritize the path with less change)
        Map<N, E> childParentMap = new HashMap<>();
        Queue<Tuple<N,Integer>> queue = new LinkedList<>();
        Queue<Tuple<N,Integer>> nextQueue = new LinkedList<>();
        queue.add(new Tuple<>(source,0));
        visited.add(source);
        N currNode;
        N neighbourNode;
        Tuple<N,Integer> currTuple;
        Integer transitions;
        //adding not seen neighbour nodes into different lists according to the label colours
        //when adding neighbours to the queue the ones with the same label are prioritized so the first path found would be with the least station changes
        ArrayList<N> nodesToEnqueueSameLabel;
        ArrayList<N> nodesToEnqueueDiffLabel;

        while(!queue.isEmpty()){

            nodesToEnqueueSameLabel = new ArrayList();
            nodesToEnqueueDiffLabel = new ArrayList();

            currTuple = queue.poll();
            currNode = currTuple.first;
            transitions = currTuple.second;

                for(E edge: adjacencyMap.get(currNode)){
                    neighbourNode = edge.getOppositeNode(currNode);
                    if(!visited.contains(neighbourNode)){
                        if(childParentMap.containsKey(currNode) && edge.getLabel().equals(childParentMap.get(currNode).getLabel())){
                            nodesToEnqueueSameLabel.add(neighbourNode);
                        }else{
                            nodesToEnqueueDiffLabel.add(neighbourNode);
                        }
                        childParentMap.put(neighbourNode, edge);
                        visited.add(neighbourNode);
                    }
                }

            nextQueue = this.addOrdered(nextQueue,nodesToTuples(nodesToEnqueueSameLabel,transitions));
            nextQueue = this.addOrdered(nextQueue,nodesToTuples(nodesToEnqueueDiffLabel,transitions+1));

            if(queue.isEmpty()){
                queue.addAll(nextQueue);
                nextQueue.clear();
            }
        }






        return reconstructPathInEdges(childParentMap, source, destination);
    }

    @Override
    public List<E> getPath(N source, N destination) {
        List<N> visited = new ArrayList<>();
        //contains a Node and edge that led to it(need to map nodes to the edge, not node, to see label of the node that led to it and prioritize the path with less change)
        Map<N, E> childParentMap = new HashMap<>();
        Queue<N> queue = new LinkedList<>();
        queue.add(source);
        visited.add(source);
        N currNode;
        N neighbourNode;
        //adding not seen neighbour nodes into different lists according to the label colours
        //when adding neighbours to the queue the ones with the same label are prioritized so the first path found would be with the least station changes
        ArrayList<N> nodesToEnqueueSameLabel;
        ArrayList<N> nodesToEnqueueDiffLabel;
        while(!queue.isEmpty()){
            nodesToEnqueueSameLabel = new ArrayList();
            nodesToEnqueueDiffLabel = new ArrayList();
            currNode = queue.poll();
            for(E edge: adjacencyMap.get(currNode)){
                neighbourNode = edge.getOppositeNode(currNode);
                if(!visited.contains(neighbourNode)){
                    if(childParentMap.containsKey(currNode) && edge.getLabel().equals(childParentMap.get(currNode).getLabel())){
                        nodesToEnqueueSameLabel.add(neighbourNode);
                    }else{
                        nodesToEnqueueDiffLabel.add(neighbourNode);
                    }
                    childParentMap.put(neighbourNode, edge);
                    visited.add(neighbourNode);
                }
            }
            for(N n: nodesToEnqueueSameLabel) queue.add(n);
            for(N n: nodesToEnqueueDiffLabel) queue.add(n);
        }
        return reconstructPathInEdges(childParentMap, source, destination);
    }

    @Override
    public List<N> reconstructPath(Map<N, E> childParentMap, N source, N destination){
        List<N> path = new ArrayList<>();
        N currNode = destination;
        while(currNode != source){
            path.add(currNode);
            currNode = childParentMap.get(currNode).getOppositeNode(currNode);
        }
        path.add(currNode);
        Collections.reverse(path);
        return path;
    }

    @Override
    public List<E> reconstructPathInEdges(Map<N, E> childParentMap, N source, N destination){
        List<E> path = new ArrayList<>();
        N currNode = destination;
        while(currNode != source){
            path.add(childParentMap.get(currNode));
            currNode = childParentMap.get(currNode).getOppositeNode(currNode);
        }
        Collections.reverse(path);
        return path;
    }

    private List<Tuple<N,Integer>> nodesToTuples(List<N> list,Integer num){
        List<Tuple<N,Integer>> newList = new ArrayList<>();
        for(N n: list) newList.add(new Tuple(n,num));
        return newList;
    }

    private Queue<Tuple<N,Integer>> addOrdered(Queue<Tuple<N,Integer>> queue, List<Tuple<N,Integer>> toadd){
        if(toadd.isEmpty()){
            return queue;
        }


        Queue<Tuple<N,Integer>> newQueue = new LinkedList<>();
        Iterator<Tuple<N,Integer>> iterator = queue.iterator();
        Integer transitions = toadd.get(0).second;

        if(queue.isEmpty()){
            newQueue.addAll(toadd);
        }

        while (iterator.hasNext()){
            Tuple<N,Integer> node = iterator.next();
            if(node.second > transitions || !iterator.hasNext()){
                newQueue.addAll(toadd);
                iterator.forEachRemaining(newQueue::add);
            }
            newQueue.add(node);
        }

        return newQueue;
    }
    public class Tuple<X, Y> {
        public final X first;
        public final Y second;
        public Tuple(X first, Y second) {
            this.first = first;
            this.second = second;
        }
    }
}
