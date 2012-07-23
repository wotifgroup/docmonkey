package com.wotifgroup.realconfu.resources;

import com.wotifgroup.realconfu.Graph2Applescript;
import com.wotifgroup.realconfu.RealConfuService;
import com.wotifgroup.realconfu.core.Graph;
import com.yammer.dropwizard.logging.Log;
import com.yammer.metrics.annotation.Timed;
import org.codehaus.jackson.map.ObjectMapper;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/generate")
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.APPLICATION_JSON)
public class DiagramGenerateResource {
    public static final ObjectMapper om = new ObjectMapper();
    private static final Log LOG = Log.forClass(RealConfuService.class);

    @GET
    public Response generate() {
//        list generated files
        return Response.ok().build();
    }

    @DELETE
    public Response delete(@QueryParam("name") String name ) throws ScriptException {
        String applescript = new Graph2Applescript().delete(name);
        new ScriptEngineManager().getEngineByName("AppleScript").eval(applescript);
        return Response.ok(applescript).build();
    }

//    export_formats
//    publish_to
    @POST
    @Timed
    public Response generate(Graph graph) throws ScriptException {
        String applescript = new Graph2Applescript().execute(graph);
        LOG.debug("received: {}", applescript);

        new ScriptEngineManager().getEngineByName("AppleScript").eval(applescript);

        return Response.ok(applescript).build();

    }

}

