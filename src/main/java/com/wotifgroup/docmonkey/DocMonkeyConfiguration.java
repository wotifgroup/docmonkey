package com.wotifgroup.docmonkey;

import com.yammer.dropwizard.config.Configuration;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

public class DocMonkeyConfiguration extends Configuration {
    @JsonProperty
    @NotEmpty
    private String exportDir;

    @JsonProperty
    @NotEmpty
    private String formats;


    public String getExportDir() {
        return exportDir;
    }

    public void setExportDir(String exportDir) {
        this.exportDir = exportDir;
    }

    public String getFormats() {
        return formats;
    }

    public void setFormats(String formats) {
        this.formats = formats;
    }
}
