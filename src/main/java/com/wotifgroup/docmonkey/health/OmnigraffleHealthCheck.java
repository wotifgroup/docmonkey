package com.wotifgroup.DocMonkey.health;

import com.yammer.metrics.core.HealthCheck;

public class OmnigraffleHealthCheck extends HealthCheck {

    public OmnigraffleHealthCheck() {
        super("omnigraffle-process");
    }


    @Override
    protected Result check() throws Exception {
        return Result.healthy("TODO: check the process, is it responding?");
    }
}
