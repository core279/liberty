package com.navercorp.pinpoint.plugin.liberty;

import com.navercorp.pinpoint.bootstrap.plugin.ApplicationTypeDetector;
import com.navercorp.pinpoint.bootstrap.resolver.ConditionProvider;
import com.navercorp.pinpoint.common.trace.ServiceType;
import com.navercorp.pinpoint.common.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by core279 on 2017. 11. 13..
 */
public class LibertyDetector implements ApplicationTypeDetector {

    private static final String DEFAULT_BOOTSTRAP_MAIN = "STAND_ALONE";

    private final List<String> bootstrapMains;

    public LibertyDetector(List<String> bootstrapMains) {

        if (CollectionUtils.isEmpty(bootstrapMains)) {
            this.bootstrapMains = Arrays.asList(DEFAULT_BOOTSTRAP_MAIN);
        } else {
            this.bootstrapMains = bootstrapMains;
        }

    }

    @Override
    public ServiceType getApplicationType() {
        return LibertyConstants.LIBERTY;
    }

    @Override
    public boolean detect(ConditionProvider provider) {
        return provider.checkMainClass(bootstrapMains) ;
    }

}
