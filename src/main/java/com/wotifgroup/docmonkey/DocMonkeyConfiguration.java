package com.wotifgroup.docmonkey;

import com.yammer.dropwizard.config.Configuration;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;

public class DocMonkeyConfiguration extends Configuration {

    @NotEmpty
    private String exportDir;
    private String formats;

    @NotEmpty
    private String autoclose;

    @NotEmpty
    private String defaultName;

    @NotEmpty
    private String canvasNumberSuffix;

    @NotEmpty
    @Pattern(regexp = "[^~]+")
    private String resourceLocation = "/dm";

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

    public String getAutoclose() {
        return autoclose;
    }

    public void setAutoclose(String autoclose) {
        this.autoclose = autoclose;
    }

    public String getDefaultName() {
        return defaultName;
    }

    public void setDefaultName(String defaultName) {
        this.defaultName = defaultName;
    }

    public String getCanvasNumberSuffix() {
        return canvasNumberSuffix;
    }

    public void setCanvasNumberSuffix(String canvasNumberSuffix) {
        this.canvasNumberSuffix = canvasNumberSuffix;
    }

    public String getResourceLocation() {
        return resourceLocation;
    }

    public void setResourceLocation(String resourceLocation) {
        this.resourceLocation = resourceLocation;
    }
}
