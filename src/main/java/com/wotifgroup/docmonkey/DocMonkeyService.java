package com.wotifgroup.docmonkey;

import com.wotifgroup.docmonkey.health.DiagramGeneratorHealthCheck;
import com.wotifgroup.docmonkey.health.OmnigraffleHealthCheck;
import com.wotifgroup.docmonkey.resources.DiagramGenerateResource;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.logging.Log;
import com.yammer.dropwizard.views.ViewBundle;
import com.yammer.dropwizard.views.ViewMessageBodyWriter;
import org.eclipse.jetty.server.handler.ResourceHandler;
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

        DefaultServlet staticServlet =  new DefaultServlet() {

            @Override
            public String getInitParameter(String name) {
                if (name.equals("resourceBase")) {
                    LOG.debug("fetch resourceBase called");
                    return configuration.getExportDir() + "/..";
                }
                return super.getInitParameter(name);    //To change body of overridden methods use File | Settings | File Templates.
            }


            @Override
            protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                LOG.debug("got request:" + request.getRequestURI());
                super.doGet(request, response);
            }
        };

        environment.addServlet(staticServlet, "/dm/*");

//        lets add this last
        environment.addResource(new DiagramGenerateResource(configuration));

// !!       perhaps the jersey container might be capturing all!!

// //        environment.addServlet(new AssetServlet(configuration.getExportDir(), CacheBuilderSpec.disableCaching(), configuration.getResourceLocation()), configuration.getResourceLocation() + '*');
    }


}


