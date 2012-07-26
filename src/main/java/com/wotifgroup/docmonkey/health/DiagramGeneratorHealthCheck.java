package com.wotifgroup.DocMonkey.health;

import com.yammer.metrics.core.HealthCheck;

public class DiagramGeneratorHealthCheck extends HealthCheck  {

    public DiagramGeneratorHealthCheck() {
        super("diagram-generator");
    }

    @Override
    protected Result check() throws Exception {
        return Result.healthy("I'm not checking anythin yet");
    }
}
