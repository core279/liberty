package com.navercorp.pinpoint.plugin.liberty;

import com.navercorp.pinpoint.bootstrap.instrument.InstrumentClass;
import com.navercorp.pinpoint.bootstrap.instrument.InstrumentException;
import com.navercorp.pinpoint.bootstrap.instrument.InstrumentMethod;
import com.navercorp.pinpoint.bootstrap.instrument.Instrumentor;
import com.navercorp.pinpoint.bootstrap.instrument.transformer.TransformCallback;
import com.navercorp.pinpoint.bootstrap.instrument.transformer.TransformTemplate;
import com.navercorp.pinpoint.bootstrap.instrument.transformer.TransformTemplateAware;
import com.navercorp.pinpoint.bootstrap.logging.PLogger;
import com.navercorp.pinpoint.bootstrap.logging.PLoggerFactory;
import com.navercorp.pinpoint.bootstrap.plugin.ProfilerPlugin;
import com.navercorp.pinpoint.bootstrap.plugin.ProfilerPluginSetupContext;
import java.security.ProtectionDomain;
import static com.navercorp.pinpoint.common.util.VarArgs.va;

import javax.servlet.ServletResponse;
import javax.servlet.ServletRequest;

/**
 * Created by core279 on 2017. 11. 13..
 */
public class LibertyPlugin implements ProfilerPlugin, TransformTemplateAware {

    private final PLogger logger = PLoggerFactory.getLogger(this.getClass());

    private TransformTemplate transformTemplate;


    @Override
    public void setup(ProfilerPluginSetupContext context) {

        final LibertyConfig config = new LibertyConfig(context.getConfig());
        if (logger.isInfoEnabled()) {
            logger.info("LibertyPlugin config:{}", config);
        }
        if (!config.isLibertyEnable()) {
            logger.info("LibertyPlugin disabled");
            return;
        }

        LibertyDetector libertyDetector = new LibertyDetector(config.getLibertyBootstrapMains());
        context.addApplicationTypeDetector(libertyDetector);

        addServerInterceptor(config);
    }


    private void addServerInterceptor(final LibertyConfig config){

        transformTemplate.transform("com.ibm.ws.webcontainer.WebContainer", new TransformCallback() {
            @Override
            public byte[] doInTransform(Instrumentor instrumentor, ClassLoader classLoader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws InstrumentException {

                // 1. Get InstrumentClass of the target class
                InstrumentClass target = instrumentor.getInstrumentClass(classLoader, className, classfileBuffer);

                // 2. Get InstrumentMethod of the target method.
                InstrumentMethod handleMethodEditorBuilder = target.getDeclaredMethod("handleRequest", "com.ibm.websphere.servlet.request.IRequest",
                        "com.ibm.websphere.servlet.response.IResponse", "com.ibm.ws.webcontainer.VirtualHost", "com.ibm.wsspi.webcontainer.RequestProcessor");

                if (handleMethodEditorBuilder != null) {
                    // 3. Add interceptor. The first argument is FQN of the interceptor class, followed by arguments for the interceptor's constructor.
                    handleMethodEditorBuilder.addInterceptor("com.navercorp.pinpoint.plugin.liberty.interceptor.LibertyServerHandleInterceptor",  va(config.getLibertyExcludeUrlFilter()));

                    // 4. Return resulting byte code.
                    return target.toBytecode();
                }

                return target.toBytecode();
            }
        });
    }


    @Override
    public void setTransformTemplate(TransformTemplate transformTemplate) {
        this.transformTemplate = transformTemplate;
    }
}