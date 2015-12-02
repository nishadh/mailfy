package com.nishadh.mailfy.rest.auth;

import com.nishadh.mailfy.model.User;
import com.nishadh.mailfy.service.OAuthTokenService;
import com.nishadh.mailfy.service.UserService;
import org.ops4j.pax.cdi.api.OsgiService;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by nishadh on 12/1/15.
 */
@Path("auth")
@Singleton
public class AuthServiceImpl implements AuthService {


    @OsgiService
    @Inject
    UserService userService;

    @OsgiService
    @Inject
    OAuthTokenService oAuthTokenService;


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("/login")
    public Response login(@FormParam("email") final String email,
                                     @FormParam("password") final String password) {
        try {
            User user = userService.findByEmailAndPassword(email, password);
            //return Response.ok(user).build();
            String token = oAuthTokenService.generateToken(user, 30);

            // Return the token on the response
            return Response.ok(String.format("{\"token\": \"%s\"}", token)).header("Access-Control-Allow-Origin", "*").build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).header("Access-Control-Allow-Origin", "*").build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/logout")
    public Response logout(@QueryParam("access_token") final String accessToken) {
        try {
            //OAuthToken oAuthToken = oAuthTokenService.findToken(token);
            oAuthTokenService.deleteToken(accessToken);

            // Return the token on the response
            return Response.ok().header("Access-Control-Allow-Origin", "*").build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).header("Access-Control-Allow-Origin", "*").build();
        }
    }
}
