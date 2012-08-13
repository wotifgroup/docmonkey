package com.wotifgroup.docmonkey.core;

import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Node {
    private String id;
    private String name;
    private String text;
    private Map<String, Object> all = new HashMap<String, Object>();

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        if (id != null ? !id.equals(node.id) : node.id != null) return false;
        if (name != null ? !name.equals(node.name) : node.name != null) return false;
        if (text != null ? !text.equals(node.text) : node.text != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        return result;
    }

    @JsonAnySetter
    public void set(String name, Object value) {
        all.put(name, value);
    }

    public Map<String, Object> getAll() {
        return all;
    }

    public void setAll(Map<String, Object> all) {
        this.all = all;
    }
    public String getId() {
        return id;
    }
}

