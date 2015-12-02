package com.nishadh.mailfy.rest.cxf;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.blueprint.BlueprintBus;
import org.apache.cxf.bus.extension.ExtensionManagerBus;
import org.osgi.framework.BundleContext;
import org.osgi.service.blueprint.container.BlueprintContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CXFBusFactory {
    private static final transient Logger LOGGER = LoggerFactory.getLogger(CXFBusFactory.class);

    private BlueprintBus bus;

    @Inject
    BundleContext bundleContext;

    @Inject
    BlueprintContainer blueprintContainer;

    @Produces
    public Bus create() {
        LOGGER.info("Create CXF BUS");
        if (bus == null) {
            bus = new BlueprintBus();
            bus.setBundleContext(bundleContext);
            bus.setBlueprintContainer(blueprintContainer);
            bus.initialize();
        }
        return bus;
    }

    @PreDestroy
    public void destroyBus() {
        LOGGER.info("Destroy CXF BUS");
        if (bus != null) {
            ((ExtensionManagerBus)bus).shutdown();
        }
    }
}
