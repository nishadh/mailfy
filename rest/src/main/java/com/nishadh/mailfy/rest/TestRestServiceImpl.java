package com.nishadh.mailfy.rest;

import com.nishadh.mailfy.model.User;
import com.nishadh.mailfy.service.OAuthTokenService;
import org.ops4j.pax.cdi.api.OsgiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Properties;

/**
 * Created by nishadh on 11/28/15.
 */
@Path("rest")
@Singleton
public class TestRestServiceImpl implements TestRestService  {

    private static final transient Logger LOGGER = LoggerFactory.getLogger(TestRestServiceImpl.class);


    @OsgiService
    @Inject
    OAuthTokenService oAuthTokenService;

    @GET
    //@LoggedIn
    @Path("/sayHello/{name}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response sayHello(@PathParam("name") final String name,
                           @QueryParam("access_token") final String accessToken) {

        LOGGER.info("Saying hello to " + name);
        LOGGER.info("Access token is " + accessToken);
        try {
            User user = oAuthTokenService.findToken(accessToken).getUser();
            return Response.ok("{\"User Email\": \""+ user.getEmail()  +"\"}").build();
        } catch (Exception ex) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
}
