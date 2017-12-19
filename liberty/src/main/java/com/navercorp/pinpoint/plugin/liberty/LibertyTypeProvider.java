package com.navercorp.pinpoint.plugin.liberty;

import com.navercorp.pinpoint.common.trace.TraceMetadataProvider;
import com.navercorp.pinpoint.common.trace.TraceMetadataSetupContext;

/**
 * Created by core279 on 2017. 11. 13..
 */
public class LibertyTypeProvider implements TraceMetadataProvider {

    @Override
    public void setup(TraceMetadataSetupContext context) {
        context.addServiceType(LibertyConstants.LIBERTY);
        context.addServiceType(LibertyConstants.LIBERTY_METHOD);
    }
}
