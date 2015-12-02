package com.nishadh.mailfy.rest;

import javax.ws.rs.core.Response;

/**
 * Created by nishadh on 11/28/15.
 */

public interface TestRestService {
    public Response sayHello(final String name, final String accessToken);
}
