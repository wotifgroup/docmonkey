package com.wotifgroup.docmonkey;


import com.wotifgroup.docmonkey.core.Graph;
import com.wotifgroup.docmonkey.core.Link;
import com.wotifgroup.docmonkey.core.Node;
import com.yammer.dropwizard.logging.Log;

public class Graph2Applescript {
    private static final Log LOG = Log.forClass(Graph2Applescript.class);
    private String exportDir;
    private String absFileName;
    private String fileName;
    private String canvasNumberSuffix;

    public Graph2Applescript(String exportDir, String fileName) {
        this.absFileName = exportDir + "/" + fileName;
        this.fileName = fileName;
        this.exportDir = exportDir;
        this.canvasNumberSuffix = "true";
    }

    public String create(Graph graph) {

        StringBuffer sb = new StringBuffer();

        sb.append("tell application \"OmniGraffle Professional 5\"\n");
        sb.append("	set theDocument to (make new document at end of documents with properties {template: \"template_1\"})\n");
        sb.append("  tell canvas of front window\n");


        sb.append(addNodes(graph));
        sb.append(addLinks(graph));

        sb.append(autolayout());
        sb.append("\tsave theDocument in \"" + absFileName + ".graffle\"\n");
        sb.append("end tell\n");
        sb.append("end tell\n");
//        LOG.debug(sb.toString());
        return sb.toString();

    }

    public String export(Graph graph) {
        StringBuffer sb = new StringBuffer();

        sb.append("on export(filelist)\n");
        sb.append("tell application \"OmniGraffle Professional 5\"\n");
        sb.append(export_png());
        sb.append("close theDocument\n");
        sb.append("end tell\n");
        sb.append("return filelist\n");
        sb.append("end export\n");
//        LOG.debug(sb.toString());
        return sb.toString();
    }

    private String autolayout() {
        return "  layout\npage adjust\n";

    }

    public String addNodes(Graph graph) {
        StringBuffer sb = new StringBuffer();
        for (Node node : graph.getNodes()) {
            sb.append("		set myshape to make new shape at end of graphics with properties {autosizing:full, text: {text: \"" + node.getText () + "\", alignment: center}, draws shadow: false, tag:\"" + node.getId()  + "\"}\n");
        }
        return sb.toString();
    }

    public String addLinks(Graph graph) {
        StringBuffer sb = new StringBuffer();
        for (Link link : graph.getLinks()) {
            sb.append("    set srcGraphic to first shape whose tag is \"" + link.getSource() + "\"\n");

            sb.append("    set targetGraphic to first shape whose tag is \"" + link.getTarget() + "\"\n");
            sb.append("    set theLine to connect srcGraphic to targetGraphic with properties {head type:\"FilledArrow\"}\n");
        }

        return sb.toString();
    }

    //from http://forums.omnigroup.com/showthread.php?t=106&highlight=export+posix
    public String export_pdf(Graph graph) {
        String export = "property exportFileExtension : \"pdf\"\n" +
                "\n" +
                "tell application \"OmniGraffle Professional 5\"\n" +
                "\t--\ttell canvas of front window\n" +
                "\tset theDocument to front document\n" +
                "\tset theWindow to front window\n" +
                "\t\n" +
                "\tset export_folder to (POSIX path of \"/Users/Brett/work/tmp\")\n" +
                "\t\n" +
                "\tset area type of current export settings to entire document\n" +
                "\tset draws background of current export settings to false\n" +
                "\t\n" +
                "\t--\trepeat with theWindow in windows\n" +
                "\tset theDocument to document of theWindow\n" +
                "\tlog \"In window \" & name of theWindow\n" +
                "\t\n" +
                "\t-- check if this a true document\n" +
                "\tset hasDocument to true\n" +
                "\ttry\n" +
                "\t\tset theFilename to name of theDocument\n" +
                "\t\tlog \"filename: \" & theFilename\n" +
                "\ton error\n" +
                "\t\tlog \"some error with:\" & theFilename\n" +
                "\t\tset hasDocument to false\n" +
                "\t\t\n" +
                "\tend try\n" +
                "\t\n" +
                "\tlog export settings of theDocument\n" +
                "\t--\tif hasDocument then\n" +
                "\tset exportFilename to export_folder & \"/\" & theFilename & \".\" & exportFileExtension\n" +
                "\tlog \"Exporting \" & exportFilename\n" +
                "\tsave theDocument in exportFilename\n" +
                "\t--\tend if\n" +
                "\t\n" +
                "\t--\tend repeat\n" +
                "end tell";
        return export;
    }


    public String delete(String posix_filename) {
        String remove_file = "tell application \"Finder\"\n" +
                "\tif (exists (POSIX file \"" + posix_filename + "\" as text)) then\n" +
                "\t\tdelete (POSIX file \"" + posix_filename + "\" as text)\n" +
                "\tend if\n" +
                "end tell\n";
        return remove_file;
    }

    public String export_png2() {
        String export = "--Get the current document for later use \n" +
                "set currentDocument to document of front window\n" +
                "\n" +
                "--Get a list of the canvases\n" +
                "set theCanvases to every canvas of currentDocument\n" +
                "\n" +
                "--Use a counter for unique naming\n" +
                "set counter to 0\n" +
                "\n" +
                "--Loop over each canvas\n" +
                "repeat with aCanvas in theCanvases\n" +
                "--Make sure that the current canvas is displayed \n" +
                "--(export of currently selected only works in the displayed window\n" +
                "set canvas of front window to aCanvas\n" +
                "\n" +
                "--get a list of groups for this canvas\n" +
                "set theGroups to every graphic of aCanvas\n" +
                "get theGroups\n" +
                "\n" +
                "--loop over each group/graphic\n" +
                "repeat with aGroup in theGroups\n" +
                "get aGroup\n" +
                "\n" +
                "--Set the selection of the window and save / export\n" +
                "set selection of front window to {aGroup}\n" +
                "save currentDocument in POSIX file (export_folder & filePrefix & (counter) & \".png\")\n" +
                "set counter to counter + 1\n" +
                "\n" +
                "end repeat\n" +
                "end repeat\n" +
                "\n";

        return export;
    }

//                    "\t-- if the file's being actively edited, a random object may be selected when we come to \n" +
//    "\t-- do the export, and with an object selected the export will default to 'selected graphics'  - oops!\n" +
//            "\t-- sooo, override the 'default' (and return it later)\n" +

    public String export_png() {
        String fileExtension = "png";
        String export = "set theDocument to front document\n\tset _path to path of theDocument\n" +
                "\t\n" +
                "\tset oldAreaType to area type of current export settings\n" +
                "\tset area type of current export settings to all graphics\n" +
                "\t\n" +
                "\tset area type of current export settings to current canvas\n" +
                "\tset draws background of current export settings to false\n" +
                "\tset include border of current export settings to false\n" +
                "\t\n" +
                "\tset export_folder to \"" + exportDir + "\"\n" +
                "\t\n" +
                "\tset theFilename to \"" + fileName + "\"\n" +
                "\tset exportFilename to export_folder & \"/\" & theFilename & \".png\"\n" +
                "\tlog \"export to \" & exportFilename\n" +
                "\tset canvasCount to count of canvases of theDocument\n" +
                "\t\n" +
                "\tset canvasNumber to 1\n" +
                "\trepeat with theCanvas in every canvas of theDocument\n" +
                "\t\tset canvas of front window to theCanvas\n" +
                "\t\tset canvas_name to name of theCanvas\n" +
                "\t\tset layerCount to count of layers of theCanvas\n" +
                "\t\t\n" +
                "\t\tset canvasFilename to \"\"\n" +
                "\t\tif " + canvasNumberSuffix  + " then\n" +
                "\t\t\tset canvasFilename to canvasNumber & \"_\"\n" +
                "\t\tend if\n" +
                "\t\tset canvasFilename to canvasFilename & canvas_name\n" +
                "\t\t\n" +
                "\t\t-- Hide all layers, except those beginning with \"*\"\n" +
                "\t\t-- also check if there is only one layer to be exported\n" +
                "\t\trepeat with layerNumber from 1 to layerCount\n" +
                "\t\t\tset theLayer to layer layerNumber of theCanvas\n" +
                "\t\t\tset number_of_layer_to_be_exported to 0\n" +
                "\t\t\tif theLayer is prints and class of theLayer is not shared layer then\n" +
                "\t\t\t\tset layer_name to name of theLayer as string\n" +
                "\t\t\t\tif character 1 of layer_name is not \"*\" then\n" +
                "\t\t\t\t\tset visible of theLayer to false\n" +
                "\t\t\t\t\tset number_of_layer_to_be_exported to number_of_layer_to_be_exported + 1\n" +
                "\t\t\t\telse\n" +
                "\t\t\t\t\tset visible of theLayer to true\n" +
                "\t\t\t\tend if\n" +
                "\t\t\tend if\n" +
                "\t\tend repeat\n" +
                "\t\trepeat with layerNumber from 1 to layerCount\n" +
                "\t\t\tset theLayer to layer layerNumber of theCanvas\n" +
                "\t\t\t\n" +
                "\t\t\tif (theLayer is prints) and (class of theLayer is not shared layer) then\n" +
                "\t\t\t\tset layer_name to name of theLayer as string\n" +
                "\t\t\t\tset filename to canvasFilename & \"_\" & layer_name & \"." + fileExtension + "\"\n" +
                "\t\t\t\tset exportFilename to export_folder & \"/\" & theFilename & \"_\" & filename\n" +
                "\t\t\t\tlog \"exportFilename: \" & exportFilename\n" +
                "\t\t\t\t\n" +
                "\t\t\t\t-- show the layer, export, then hide the layer\n" +
                "\t\t\t\tif character 1 of layer_name is not \"*\" then\n" +
                "\t\t\t\t\tset visible of theLayer to true\n" +
                "\t\t\t\t\tsave theDocument in exportFilename\n" +
                "\t\t\t\t\tif filelist is not \"\" then\n" +
                "\t\t\t\t\tset filelist to filelist & \", \" & exportFilename\n" +
                "\t\t\t\t\telse\n" +
                "\t\t\t\t\tset filelist to filelist & exportFilename\n" +
                "\t\t\t\t\tend if\n" +
                "\t\t\t\t\tset visible of theLayer to false\n" +
                "\t\t\t\tend if\n" +
                "\t\t\tend if\n" +
                "\t\tend repeat\n" +
                "\t\tset canvasNumber to canvasNumber + 1\n" +
                "\tend repeat\n";
        return export;
    }


}
