package com.wotifgroup.docmonkey.resources;


import com.wotifgroup.docmonkey.DocMonkeyConfiguration;
import com.wotifgroup.docmonkey.Graph2Applescript;
import com.wotifgroup.docmonkey.core.Export;
import com.wotifgroup.docmonkey.core.Graph;
import com.wotifgroup.docmonkey.views.ExportView;
import com.yammer.dropwizard.logging.Log;
import com.yammer.metrics.annotation.Metered;

import javax.script.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/generate")
@Produces(MediaType.TEXT_HTML)
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
    @Produces(MediaType.TEXT_HTML)
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
    public ExportView generate(@DefaultValue("test") @QueryParam("name") String name, Graph graph) throws ScriptException {
        String title = name;
        String applescript = new Graph2Applescript(config).create(graph);
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("AppleScript");

        engine.eval(applescript);
        applescript = new Graph2Applescript(config).export(graph);

        String filelist = "";
        ScriptContext ctx = engine.getContext();
        Bindings bindings = ctx.getBindings(ScriptContext.ENGINE_SCOPE);
        bindings.put("javax_script_function", "export");
        bindings.put(ScriptEngine.ARGV, filelist);

        Object retVal = "";
        retVal = engine.eval(applescript, ctx);
        LOG.debug("filelist:" + retVal.toString());

        return new ExportView(new Export(name, title, config.getResourceLocation(), config.getExportDir(), (String)retVal));

    }

}

