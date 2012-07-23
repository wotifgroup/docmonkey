package com.wotifgroup.realconfu.views;

import com.wotifgroup.realconfu.core.Graph;
import com.yammer.dropwizard.views.View;

public class GraphApplescriptView extends View {
    private Graph graph;

    public GraphApplescriptView(Graph graph) {
        super("graph_applescript.ftl");

        this.graph = graph;
    }

    protected GraphApplescriptView(String templateName) {
        super(templateName);
    }


}
