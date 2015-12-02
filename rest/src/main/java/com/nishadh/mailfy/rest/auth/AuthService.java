package com.nishadh.mailfy.rest.auth;

import javax.ws.rs.core.Response;

/**
 * Created by nishadh on 12/1/15.
 */
public interface AuthService {
    public Response login(final String username, final String password);
    public Response logout(final String accessToken);
}


