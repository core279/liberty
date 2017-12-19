package com.navercorp.pinpoint.plugin.liberty;

import com.navercorp.pinpoint.common.trace.ServiceType;
import com.navercorp.pinpoint.common.trace.ServiceTypeFactory;

import static com.navercorp.pinpoint.common.trace.ServiceTypeProperty.RECORD_STATISTICS;

/**
 * Created by core279 on 2017. 11. 13..
 */
public class LibertyConstants {

    private LibertyConstants() {
    }

    public static final String TYPE_NAME = "LIBERTY";

    public static final ServiceType LIBERTY = ServiceTypeFactory.of(1070, "LIBERTY", RECORD_STATISTICS);
    public static final ServiceType LIBERTY_METHOD = ServiceTypeFactory.of(1071, "LIBERTY_METHOD");

    public static final String METADATA_TRACE = "trace";
    public static final String METADATA_ASYNC = "async";
    public static final String METADATA_ASYNC_TRACE_ID = "asyncTraceId";

    public static final String ATTRIBUTE_PINPOINT_TRACE = "PINPOINT_TRACE";
}
