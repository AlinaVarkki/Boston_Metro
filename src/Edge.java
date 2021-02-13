interface Edge <N>{
    N getOppositeNode(N node);
    String getLabel();
    N getNode1();
    N getNode2();
    void setNode1(N node);
    void setNode2(N node);
}
