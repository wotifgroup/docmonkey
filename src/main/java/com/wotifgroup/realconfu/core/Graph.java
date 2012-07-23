package com.wotifgroup.realconfu.core;


import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Graph {

    private List Nodes;
    private List Links;

    public List<Node> getNodes() {
        return Nodes;
    }

    public void setNodes(List<Node> nodes) {
        Nodes = nodes;
    }

    public List<Link> getLinks() {
        return Links;
    }

    public void setLinks(List<Link> links) {
        Links = links;
    }
}
