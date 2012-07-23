package com.wotifgroup.realconfu.core;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Link {
    private String source;
    private String target;

    @JsonProperty("s")
    public String getSource() {
        return source;
    }

    @JsonProperty("s")
    public void setSource(String source) {
        this.source = source;
    }
    @JsonProperty("t")
    public String getTarget() {
        return target;
    }

    @JsonProperty("t")
    public void setTarget(String target) {
        this.target = target;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Link link = (Link) o;

        if (source != null ? !source.equals(link.source) : link.source != null) return false;
        if (target != null ? !target.equals(link.target) : link.target != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = source != null ? source.hashCode() : 0;
        result = 31 * result + (target != null ? target.hashCode() : 0);
        return result;
    }
}
