package com.wotifgroup.docmonkey.core;


import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Graph {

    private List Nodes;
    private List Links;
    private Boolean directed;
    private Boolean multigraph;

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

    public Boolean getDirected() {
        return directed;
    }

    public void setDirected(Boolean directed) {
        this.directed = directed;
    }

    public Boolean getMultigraph() {
        return multigraph;
    }

    public void setMultigraph(Boolean multigraph) {
        this.multigraph = multigraph;
    }
}
