package com.nishadh.mailfy.rest.cxf;

import com.nishadh.mailfy.rest.TestRestService;
import com.nishadh.mailfy.rest.api.EmailAlertApiService;
import com.nishadh.mailfy.rest.auth.AuthService;
import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.ext.logging.LoggingFeature;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Singleton;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.singletonList;

@Singleton
public class MailfyRestExporter {

    @Inject
    EmailAlertApiService emailAlertApiService;

    @Inject
    AuthService authService;

    @Inject
    Bus bus;

    private Server server;

    @PostConstruct
    public void create() {
        JAXRSServerFactoryBean factory = new JAXRSServerFactoryBean();
        factory.setAddress("/mailfy");
        List<Object> serviceBeans = new ArrayList<>();
        serviceBeans.add(authService);
        serviceBeans.add(emailAlertApiService);
        factory.setServiceBeans(serviceBeans);
        factory.setResourceClasses();
        factory.setBus(bus);
        factory.setFeatures(singletonList(new LoggingFeature()));
        server = factory.create();
        server.start();
    }


    @PreDestroy
    public void destroy() {
        server.destroy();
    }
}
