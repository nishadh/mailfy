package com.nishadh.mailfy.rest.api;

import com.nishadh.mailfy.model.EmailAlert;

import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.Collection;

/**
 * Created by nishadh on 12/2/15.
 */
public interface EmailAlertApiService {
    public Response createEmailAlert(final String title,
                                     final String message,
                                     final String targetDate,
                                     final String accessToken);
    public Response getEmailAlerts(final String accessToken);
}
