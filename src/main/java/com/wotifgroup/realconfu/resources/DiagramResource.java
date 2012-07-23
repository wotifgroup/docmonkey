package com.wotifgroup.realconfu.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("diagram")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DiagramResource {

    @Path("/")
    @GET()
    public Response get() {
        return Response.ok().build();
    }
    @Path("generate")
    public Response generate(String content) {
        //queue document generation
        return Response.ok().build();
    }
}
