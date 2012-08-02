package com.wotifgroup.docmonkey;

import com.google.common.io.Files;
import com.wotifgroup.docmonkey.health.DiagramGeneratorHealthCheck;
import com.wotifgroup.docmonkey.health.OmnigraffleHealthCheck;
import com.wotifgroup.docmonkey.resources.DiagramGenerateResource;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.logging.Log;
import com.yammer.dropwizard.views.ViewBundle;
import com.yammer.dropwizard.views.ViewMessageBodyWriter;
import org.eclipse.jetty.servlet.DefaultServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DocMonkeyService extends Service<DocMonkeyConfiguration> {
    private static final Log LOG = Log.forClass(DocMonkeyService.class);


    public static void main(String[] args) throws Exception {
        new DocMonkeyService().run(args);
    }

    private DocMonkeyService() {
        super("DocMonkey");
        addBundle(new ViewBundle());
    }

    @Override
    protected void initialize(final DocMonkeyConfiguration configuration,
                              Environment environment) throws ClassNotFoundException {

        environment.addHealthCheck(new DiagramGeneratorHealthCheck());
        environment.addHealthCheck(new OmnigraffleHealthCheck());
        environment.addProvider(ViewMessageBodyWriter.class);

        FileStaticAssetServlet staticServlet = new FileStaticAssetServlet(configuration.getExportDir());

        environment.addServlet(staticServlet, configuration.getResourceLocation() + "/*");
        environment.addResource(new DiagramGenerateResource(configuration));

    }

class FileStaticAssetServlet extends DefaultServlet {
    private final Log LOG = Log.forClass(FileStaticAssetServlet.class);
    private String resourceBase;

    public FileStaticAssetServlet(String resourceBase) {
        this.resourceBase= Files.simplifyPath(resourceBase);
    }

    @Override
    public String getInitParameter(String name) {
        if (name.equals("resourceBase")) {
            return resourceBase;
        }
        return super.getInitParameter(name);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("got request:" + request.getRequestURI());
        super.doGet(request, response);
    }
}

}


