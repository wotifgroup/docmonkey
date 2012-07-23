package com.wotifgroup.realconfu;

import com.wotifgroup.realconfu.health.DiagramGeneratorHealthCheck;
import com.wotifgroup.realconfu.resources.DiagramGenerateResource;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.views.ViewBundle;

public class RealConfuService extends Service<RealConfuConfiguration> {


    public static void main(String[] args) throws Exception {
        new RealConfuService().run(args);
    }

    private RealConfuService() {
        super("realconfu");
    }

    @Override
    protected void initialize(RealConfuConfiguration configuration,
                              Environment environment) throws ClassNotFoundException {

        environment.addResource(new DiagramGenerateResource());
        environment.addHealthCheck(new DiagramGeneratorHealthCheck());
        addBundle(new ViewBundle());

    }


}


