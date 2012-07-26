package com.wotifgroup.docmonkey.resources;


import com.wotifgroup.docmonkey.DocMonkeyConfiguration;
import com.wotifgroup.docmonkey.DocMonkeyService;
import com.wotifgroup.docmonkey.Graph2Applescript;
import com.wotifgroup.docmonkey.core.Graph;
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
    private static final Log LOG = Log.forClass(DocMonkeyService.class);
    private DocMonkeyConfiguration config;


    public DiagramGenerateResource() {
        super();
    }

    public DiagramGenerateResource(DocMonkeyConfiguration config) {
        this();
        this.config = config;
    }

    @GET
    public Response generate() {
//        list generated files
        return Response.ok().build();
    }

    @DELETE
    public Response delete(@QueryParam("name") String name ) throws ScriptException {
        String applescript = new Graph2Applescript(config).delete(name);
        new ScriptEngineManager().getEngineByName("AppleScript").eval(applescript);
        return Response.ok(applescript).build();
    }

//    export_formats
//    publish_to
    @POST
    @Timed
    public Response generate(Graph graph) throws ScriptException {
        String applescript = new Graph2Applescript(config).execute(graph);
        LOG.debug("received: {}", applescript);

        new ScriptEngineManager().getEngineByName("AppleScript").eval(applescript);

        return Response.ok(applescript).build();

    }

}

