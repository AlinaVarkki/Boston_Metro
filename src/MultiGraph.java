import javafx.util.Pair;

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
        //List<E> node2Adj = adjacencyMap.get(edge.getNode2());
        if(node1Adj != null) node1Adj.add(edge);
        //if(node2Adj != null) node2Adj.add(edge);
    }

    /*public List<E> getPathTuple(N source, N destination) {
        List<N> visited = new ArrayList<>();
        Map<N, E> childParentMap = new HashMap<>();

        Queue<Tuple<N,Integer>> queue = new LinkedList<>();
        Queue<Tuple<N,Integer>> nextQueue = new LinkedList<>();
        queue.add(new Tuple<>(source,0));
        visited.add(source);
        N currNode;
        N neighbourNode;
        Tuple<N,Integer> currTuple;
        Integer transitions;
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
    }*/

    @Override
    public List<E> getPath(N source, N destination) {
        List<N> visited = new ArrayList<>();
        Queue<Triple<N,List<E>,Integer>> queue = new LinkedList<>();
        Queue<Triple<N,List<E>,Integer>> nextQueue = new LinkedList<>();

        queue.add(new Triple<>(source,new LinkedList<>(),0));
        Triple<N,List<E>,Integer> currTuple;
        N currNode;
        List<E> currPath;
        Integer transitions;
        N neighbourNode;

        ArrayList<Triple<N,List<E>,Integer>> nodesToEnqueueSameLabel;
        ArrayList<Triple<N,List<E>,Integer>> nodesToEnqueueDiffLabel;

        while(!queue.isEmpty()){

            nodesToEnqueueSameLabel = new ArrayList<>();
            nodesToEnqueueDiffLabel = new ArrayList<>();

            currTuple = queue.poll();
            currNode = currTuple.first;
            currPath = currTuple.second;
            transitions = currTuple.third;

            if(currNode == destination){
                queue.clear();
                visited.add(currNode);
                return currPath;
            }

            if(true) {
                visited.add(currNode);
                List<E> adjEdges = adjacencyMap.get(currNode);
                for(E edge: adjEdges){
                    neighbourNode = edge.getOppositeNode(currNode);
                    if(!visited.contains(neighbourNode)){
                        if(currPath.size() > 0 && edge.getLabel().equals(currPath.get(currPath.size()-1).getLabel())){
                            List<E> newPath = new LinkedList<>(currPath);
                            newPath.add(edge);
                            nodesToEnqueueSameLabel.add(new Triple<>(neighbourNode,newPath,transitions));
                        }else{
                            List<E> newPath = new LinkedList<>(currPath);
                            newPath.add(edge);
                            nodesToEnqueueDiffLabel.add(new Triple<>(neighbourNode,newPath,transitions+1));
                        }
                    }
                }
                nextQueue = this.addOrdered(nextQueue,nodesToEnqueueSameLabel);
                nextQueue = this.addOrdered(nextQueue,nodesToEnqueueDiffLabel);

                if(queue.isEmpty()){
                    queue.addAll(nextQueue);
                    nextQueue.clear();
                }
            }
        }
        System.out.println("No path found");
        return new LinkedList<E>();

    }


    //return a path with the least switches by always prioritizing exploring the line that the last explored node is on
    //to continue exploring he nodes that have been seen before, visited list keeps track of the line it has been visited from along with the node
    //because of the way the algorithm is implemented, it needs to sometimes saves the edges that have not been visited in the specific path so cycles need to be removed before returning the path
    @Override
    public List<E> getPathDFS(N source, N destination) {
        List<String> visited = new ArrayList<>();
        Deque<Pair<N, List<E>>> deque = new LinkedList<>();

        deque.add(new Pair<>(source,new LinkedList<>()));
        Pair<N,List<E>> currPair;
        N currNode;
        List<E> currPath;
        N neighbourNode;

        while(!deque.isEmpty()){
            currPair = deque.poll();
            currNode = currPair.getKey();
            currPath = currPair.getValue();

            if(currNode == destination){
                deque.clear();
//                visited.add(currNode);
                return removeCycle(currPath);
            }

            if(true){
                if(currPath.size() > 0){
                    String labelFrom = currPath.get(currPath.size()-1).getLabel();
                    visited.add(currNode.toString() + labelFrom);
                }else{
                    visited.add(currNode.toString() + "any");
                }
                List<E> adjEdges = adjacencyMap.get(currNode);
                for(E edge: adjEdges){
                    neighbourNode = edge.getOppositeNode(currNode);
                    String p1 = neighbourNode.toString() + edge.getLabel();
                    String p2 = neighbourNode.toString() + "any";
                    if(!visited.contains(p1) && !visited.contains(p2)){
                        List<E> newPath = new LinkedList<>(currPath);
                        newPath.add(edge);
                        //always put in front the edges that have the same label so the line would get explored first
                        if(currPath.size() > 0 && edge.getLabel().equals(currPath.get(currPath.size()-1).getLabel())){
                            deque.addFirst(new Pair<>(neighbourNode, newPath));
                        }else{
                            deque.add(new Pair<>(neighbourNode, newPath));
                        }
                    }
                }
            }
        }
        System.out.println("No path found");
        return new LinkedList<E>();
    }

    public List<E> removeCycle(List<E> path){
        List<E> pathNoCycle = new ArrayList<>();
        List<String> edges = new ArrayList<>();
        for(E edge: path){
            String currReversePath = edge.getNode2() + edge.getLabel() + edge.getNode1();
            if(!edges.contains(currReversePath)){
                pathNoCycle.add(edge);
                edges.add(edge.getNode1() + edge.getLabel() + edge.getNode2());
            }else{
                pathNoCycle.remove(pathNoCycle.size() - 1);
            }
        }
        return pathNoCycle;
    }

    /*public List<E> getPath(N source, N destination) {
        List<N> visited = new ArrayList<>();
        //contains a Node and edge that led to it(need to map nodes to the edge, not node, to see label of the node that led to it and prioritize the path with less change)
        Map<N, E> childParentMap = new HashMap<>();
        Queue<N> queue = new LinkedList<>();
        queue.add(source);
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
            if(currNode == destination){
                queue.clear();
                visited.add(currNode);
            }


            if(!visited.contains(currNode)) {
                visited.add(currNode);
                List<E> adjEdges = adjacencyMap.get(currNode);
                for (E edge : adjEdges) {
                    neighbourNode = edge.getOppositeNode(currNode);
                    if (childParentMap.containsKey(currNode) && edge.getLabel().equals(childParentMap.get(currNode).getLabel())) {
                        nodesToEnqueueSameLabel.add(neighbourNode);
                    } else {
                        nodesToEnqueueDiffLabel.add(neighbourNode);
                    }
                    if(!childParentMap.containsKey(neighbourNode)){
                    childParentMap.put(neighbourNode, edge);}
                }
                queue.addAll(nodesToEnqueueSameLabel);
                queue.addAll(nodesToEnqueueDiffLabel);
            }
            }
        return reconstructPathInEdges(childParentMap, source, destination);
    }*/

    /*public List<E> getPath(N source, N destination) {
        List<N> visited = new ArrayList<>();
        //contains a Node and edge that led to it(need to map nodes to the edge, not node, to see label of the node that led to it and prioritize the path with less change)
        Map<N, E> childParentMap = new HashMap<>();
        Queue<N> queue = new LinkedList<>();
        queue.add(source);
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
            visited.add(currNode);
            List<E> adjEdges = adjacencyMap.get(currNode);
            for(E edge: adjEdges){
                neighbourNode = edge.getOppositeNode(currNode);
                if(!visited.contains(neighbourNode) && !queue.contains(neighbourNode)){
                    if(childParentMap.containsKey(currNode) && edge.getLabel().equals(childParentMap.get(currNode).getLabel())){
                        nodesToEnqueueSameLabel.add(neighbourNode);
                    }else{
                        nodesToEnqueueDiffLabel.add(neighbourNode);
                    }
                    childParentMap.put(neighbourNode, edge);
                }
            }
            for(N n: nodesToEnqueueSameLabel) queue.add(n);
            for(N n: nodesToEnqueueDiffLabel) queue.add(n);
        }
        return reconstructPathInEdges(childParentMap, source, destination);
    }*/

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


    private List<Triple<N,List<E>,Integer>> nodesToTriples(List<N> list,List<E> path,Integer num){
        List<Triple<N,List<E>,Integer>> newList = new ArrayList<>();
        for(N n: list) newList.add(new Triple(n,path,num));
        return newList;
    }

    private Queue<Triple<N,List<E>,Integer>> addOrdered(Queue<Triple<N,List<E>,Integer>> queue, List<Triple<N,List<E>,Integer>> toAdd){
        if(toAdd.isEmpty()){
            return queue;
        }


        Queue<Triple<N,List<E>,Integer>> newQueue = new LinkedList<>();
        Iterator<Triple<N,List<E>,Integer>> iterator = queue.iterator();
        Integer transitions = toAdd.get(0).third;

        if(queue.isEmpty()){
            newQueue.addAll(toAdd);
        }

        while (iterator.hasNext()){
            Triple<N,List<E>,Integer> node = iterator.next();
            if(node.third > transitions || !iterator.hasNext()){
                newQueue.addAll(toAdd);
                iterator.forEachRemaining(newQueue::add);
            }
            newQueue.add(node);
        }

        return newQueue;
    }

}
