package com.wotifgroup.docmonkey.views;

import com.wotifgroup.docmonkey.core.Export;
import com.yammer.dropwizard.views.View;

public class ExportView extends View {
    private final Export export;

    public ExportView(Export export) {
        super("diagram-details.ftl");
        this.export = export;
    }

    public Export getExport() {
        return export;
    }
}
