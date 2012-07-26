package com.wotifgroup.docmonkey;

import com.wotifgroup.docmonkey.health.DiagramGeneratorHealthCheck;
import com.wotifgroup.docmonkey.health.OmnigraffleHealthCheck;
import com.wotifgroup.docmonkey.resources.DiagramGenerateResource;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.views.ViewBundle;

public class DocMonkeyService extends Service<DocMonkeyConfiguration> {


    public static void main(String[] args) throws Exception {
        new DocMonkeyService().run(args);
    }

    private DocMonkeyService() {
        super("DocMonkey");
    }

    @Override
    protected void initialize(DocMonkeyConfiguration configuration,
                              Environment environment) throws ClassNotFoundException {

        environment.addResource(new DiagramGenerateResource());
        environment.addHealthCheck(new DiagramGeneratorHealthCheck());
        environment.addHealthCheck(new OmnigraffleHealthCheck());
        addBundle(new ViewBundle());

    }


}


