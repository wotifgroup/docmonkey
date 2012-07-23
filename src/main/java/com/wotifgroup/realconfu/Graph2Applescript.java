package com.wotifgroup.realconfu;

import com.wotifgroup.realconfu.core.Graph;
import com.wotifgroup.realconfu.core.Link;
import com.wotifgroup.realconfu.core.Node;

public class Graph2Applescript {

    public String execute(Graph graph) {
        StringBuffer sb = new StringBuffer();

        sb.append("tell application \"OmniGraffle Professional 5\"\n");
        sb.append("	set myDocument to (make new document at end of documents with properties {template: \"template_1\"})\n");

        sb.append(addNodes(graph));
        sb.append(addLinks(graph));

        sb.append(autolayout());
        sb.append("end tell");

        return sb.toString();

    }

    private String autolayout() {
        return "  layout\npage adjust\n";

    }

    public String addNodes(Graph graph) {
        StringBuffer sb = new StringBuffer();
        for (Node node : graph.getNodes()) {
            sb.append("		set myshape to make new shape at end of graphics with properties {autosizing:full, text: {text: \"" + node.getText () + "\", alignment: center}, draws shadow: false, tag:\"" + node.getId()  + "\"}\n");
        }

        return sb.toString();
    }

    public String addLinks(Graph graph) {
        StringBuffer sb = new StringBuffer();
        for (Link link : graph.getLinks()) {
            sb.append("    set srcGraphic to first shape whose tag is \"" + link.getSource() + "\"\n");

            sb.append("    set targetGraphic to first shape whose tag is \"" + link.getTarget() + "\"\n");
            sb.append("    set theLine to connect srcGraphic to targetGraphic with properties {head type:\"FilledArrow\"}\n");
        }

        return sb.toString();
    }

}
