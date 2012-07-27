package com.wotifgroup.docmonkey.resources;


import com.wotifgroup.docmonkey.DocMonkeyConfiguration;
import com.wotifgroup.docmonkey.DocMonkeyService;
import com.wotifgroup.docmonkey.Graph2Applescript;
import com.wotifgroup.docmonkey.core.Graph;
import com.yammer.dropwizard.logging.Log;
import com.yammer.metrics.annotation.Metered;
import com.yammer.metrics.annotation.Timed;
import org.codehaus.jackson.map.ObjectMapper;

import javax.script.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/generate")
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.APPLICATION_JSON)
public class DiagramGenerateResource {
    private static final Log LOG = Log.forClass(DiagramGenerateResource.class);
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
        return Response.ok().build();
    }

    @DELETE
    public Response delete(@QueryParam("name") String name ) throws ScriptException {
        String applescript = new Graph2Applescript(config).delete(name);
        new ScriptEngineManager().getEngineByName("AppleScript").eval(applescript);
        return Response.ok(applescript).build();
    }

    @POST
    @Metered(name="generate")
    public Response generate(Graph graph) throws ScriptException {
        String applescript = new Graph2Applescript(config).create(graph);
//        LOG.debug("received: {}", applescript);
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("AppleScript");
        engine.eval(applescript);


         applescript = new Graph2Applescript(config).export(graph);
        String filelist = "";
        ScriptContext ctx = engine.getContext();
        Bindings bindings = ctx.getBindings(ScriptContext.ENGINE_SCOPE);
        bindings.put("javax_script_function", "export");
        bindings.put(ScriptEngine.ARGV, filelist);

        Object retVal = engine.eval(applescript, ctx);

        LOG.debug("filelist:" + retVal.toString());

        return Response.ok().build();

//        String script = "on testList(image_list)\n" +
//                "\tset image_list to image_list & \", a\"\n" +
//                "\tset image_list to image_list & \", b\"\n" +
//                "return image_list\n" +
//                "end testList\n";
//

    }

}

