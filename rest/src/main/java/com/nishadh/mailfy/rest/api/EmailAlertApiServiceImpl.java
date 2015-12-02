package com.nishadh.mailfy.rest.api;

import com.nishadh.mailfy.model.EmailAlert;
import com.nishadh.mailfy.model.User;
import com.nishadh.mailfy.rest.auth.AuthService;
import com.nishadh.mailfy.service.EmailAlertService;
import com.nishadh.mailfy.service.OAuthTokenService;
import com.nishadh.mailfy.service.UserService;
import org.ops4j.pax.cdi.api.OsgiService;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.NoResultException;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by nishadh on 12/2/15.
 */

@Path("emailalerts")
@Singleton
public class EmailAlertApiServiceImpl implements EmailAlertApiService {

    @OsgiService
    @Inject
    OAuthTokenService oAuthTokenService;

    @OsgiService
    @Inject
    EmailAlertService emailAlertService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("/")
    public Response createEmailAlert(@FormParam("title") final String title,
                                     @FormParam("message") final String message,
                                     @FormParam("target_date") final String targetDate,
                                     @QueryParam("access_token") final String accessToken) {
        try {
            User user = oAuthTokenService.findToken(accessToken).getUser();
            EmailAlert emailAlert = new EmailAlert();
            emailAlert.setUser(user);
            emailAlert.setTitle(title);
            emailAlert.setMessage(message);

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            emailAlert.setCreatedAt(date);
            date = dateFormat.parse(targetDate); // Parse Exception will case 400
            emailAlert.setTargetDate(date);
            emailAlertService.create(emailAlert);

            return Response.ok().header("Access-Control-Allow-Origin", "*").build();
        } catch (NoResultException ex) {
            return Response.status(Response.Status.UNAUTHORIZED).header("Access-Control-Allow-Origin", "*").build();
        } catch (Exception ex) {
            return Response.status(Response.Status.BAD_REQUEST).header("Access-Control-Allow-Origin", "*").build();
        }
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmailAlerts(@QueryParam("access_token") final String accessToken) {
        try {
            User user = oAuthTokenService.findToken(accessToken).getUser();
            List<EmailAlert> emailAlerts = emailAlertService.findByUser(user);
            GenericEntity<List<EmailAlert>> emailAlertsJson = new GenericEntity<List<EmailAlert>>(emailAlerts) {
            };
            return Response.ok(emailAlertsJson).header("Access-Control-Allow-Origin", "*").build();
        } catch (Exception ex) {
            return Response.status(Response.Status.UNAUTHORIZED).header("Access-Control-Allow-Origin", "*").build();
        }
    }
}
