package com.intuit.accountant.services.dcm.resources;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * Created by sshashidhar on 06/09/18.
 */

@Component
@Path("/sample")
@Api(value = "/sample", description = "sample endpoint.")
public class TestResource {

    @GET
    @Path("/")
    public Response getServiceHealth() {
        Response response = Response.ok().entity("Health Check Ok - Sample").build();
        return response;
    }

}
