interface Edge<N> {
    N getOppositeNode(N node);

    String getLabel();

    N getNode1();

    N getNode2();
}
