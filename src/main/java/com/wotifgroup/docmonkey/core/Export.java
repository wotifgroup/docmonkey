package com.wotifgroup.docmonkey.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Export {
    private String dir;
    private String uri;
    private String title;
    private String name;
    private List<String> links;


    public Export(String name, String title, String uri, String dir, String imageList) {
        this.name = name;
        this.title = title;
        this.uri = uri;
        this.dir = dir;
        this.links = new ArrayList<String>();
        String trimList = imageList.replaceAll(dir, "");
        for (String item : trimList.split(",")) {
            getLinks().add(item);
        }
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public List<String> getLinks() {
        return links;
    }

    public void setLinks(List<String> links) {
        this.links = links;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
