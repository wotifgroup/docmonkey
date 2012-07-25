package com.wotifgroup.realconfu;

import com.wotifgroup.realconfu.core.Graph;
import com.wotifgroup.realconfu.core.Link;
import com.wotifgroup.realconfu.core.Node;

public class Graph2Applescript {

    public String execute(Graph graph) {

        StringBuffer sb = new StringBuffer();

        sb.append("tell application \"OmniGraffle Professional 5\"\n");
        sb.append("	set theDocument to (make new document at end of documents with properties {name:\"test\", template: \"template_1\"})\n");
        sb.append("  tell canvas of front window\n");


        sb.append(addNodes(graph));
        sb.append(addLinks(graph));

        sb.append(autolayout());
        sb.append("\tsave theDocument\n");
        sb.append("end tell\n");
        sb.append("end tell\n");

        sb.append("tell application \"OmniGraffle Professional 5\"\n");
        sb.append(export_png(true));
        sb.append("end tell\n");
//        check with finder if the file exists, iterate till we find one.

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

    public String export_png(Boolean add_canvas_number_suffix) {
        String fileExtension = "png";
        String export = "set theDocument to front document\n\tset _path to path of theDocument\n" +
                "\t\n" +
                "\t-- if the file's being actively edited, a random object may be selected when we come to \n" +
                "\t-- do the export, and with an object selected the export will default to 'selected graphics'  - oops!\n" +
                "\t-- sooo, override the 'default' (and return it later)\n" +
                "\tset oldAreaType to area type of current export settings\n" +
                "\tset area type of current export settings to all graphics\n" +
                "\t\n" +
                "\tset area type of current export settings to current canvas\n" +
                "\tset draws background of current export settings to false\n" +
                "\tset include border of current export settings to false\n" +
                "\t\n" +
                "\tset export_folder to (POSIX path of \"/Users/Brett/work/tmp/export\")\n" +
                "\tlog \"export_folder: \" & export_folder\n" +
                "\t\n" +
                "\t--if folder_exists(export_folder) then\n" +
                "\t--\tlog \"export_folder already exists: \" & export_folder\n" +
                "\t--end if\n" +
                "\t\n" +
                "\tset theFilename to name of theDocument\n" +
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
                "\t\tif " + add_canvas_number_suffix + " then\n" +
                "\t\t\tset canvasFilename to canvasNumber & \"- \"\n" +
                "\t\tend if\n" +
                "\t\tset canvasFilename to canvasFilename & canvas_name\n" +
                "\t\t\n" +
                "\t\t-- Hide all layers, except those beginning with \"*\"\n" +
                "\t\t-- also check if there is only one layer to be exported\n" +
                "\t\trepeat with layerNumber from 1 to layerCount\n" +
                "\t\t\tset theLayer to layer layerNumber of theCanvas\n" +
                "\t\t\tset number_of_layer_to_be_exported to 0\n" +
                "\t\t\t\n" +
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
                "\t\t\n" +
                "\t\trepeat with layerNumber from 1 to layerCount\n" +
                "\t\t\tset theLayer to layer layerNumber of theCanvas\n" +
                "\t\t\t\n" +
                "\t\t\tif (theLayer is prints) and (class of theLayer is not shared layer) then\n" +
                "\t\t\t\tset layer_name to name of theLayer as string\n" +
                "\t\t\t\tset filename to canvasFilename & \" - \" & layer_name & \"." + fileExtension + "\"\n" +
                "\t\t\t\tset exportFilename to export_folder & \"/\" & theFilename & filename\n" +
                "\t\t\t\t--\t\t\t\tset exportFilename to export_folder & filename\n" +
                "\t\t\t\tlog \"exportFilename: \" & exportFilename\n" +
                "\t\t\t\t\n" +
                "\t\t\t\t-- show the layer, export, then hide the layer\n" +
                "\t\t\t\tif character 1 of layer_name is not \"*\" then\n" +
                "\t\t\t\t\tset visible of theLayer to true\n" +
                "\t\t\t\t\tsave theDocument in exportFilename\n" +
                "\t\t\t\t\tset visible of theLayer to false\n" +
                "\t\t\t\tend if\n" +
                "\t\t\tend if\n" +
                "\t\tend repeat\n" +
                "\t\tset canvasNumber to canvasNumber + 1\n" +
                "\tend repeat\n";
        return export;
    }

    public String export_png_orig() {
        String export = "-- export all layers to image files\n" +
                "-- Copyright (c) 2011, Charles-Axel Dein\n" +
                "\n" +
                "-- Settings\n" +
                "property exportFileExtension : \"png\"\n" +
                "property ADD_CANVAS_NUMBER : true\n" +
                "-- End of Settings\n" +
                "\n" +
                "on file_exists(FileOrFolderToCheckString)\n" +
                "\ttry\n" +
                "\t\talias FileOrFolderToCheckString\n" +
                "\t\treturn true\n" +
                "\ton error\n" +
                "\t\treturn false\n" +
                "\tend try\n" +
                "end file_exists\n" +
                "\n" +
                "tell application \"OmniGraffle Professional 5\"\n" +
                "\tset theDocument to front document\n" +
                "\tset _path to path of theDocument\n" +
                "\t\n" +
                "\tset area type of current export settings to current canvas\n" +
                "\tset draws background of current export settings to false\n" +
                "\tset include border of current export settings to false\n" +
                "\t\n" +
                "\t\n" +
                "\t-- Get filename without extension\n" +
                "\ttell application \"Finder\"\n" +
                "\t\tset {theFilename, _extension, _ishidden} to the Â\n" +
                "\t\t\t{displayed name, name extension, extension hidden} Â\n" +
                "\t\t\t\tof ((the _path as POSIX file) as alias)\n" +
                "\tend tell\n" +
                "\tif (_extension ­ missing value) then\n" +
                "\t\tset theFilename to texts 1 thru -((length of _extension) + 2) of theFilename\n" +
                "\tend if\n" +
                "\t\n" +
                "\tset export_folder to (choose folder with prompt \"Pick the destination folder\") as string\n" +
                "\tset export_folder to export_folder & theFilename & \":\"\n" +
                "\t\n" +
                "\t-- Create folder if does not exist, remove it otherwise\n" +
                "\t-- Shell script should not be executed inside tell application block\n" +
                "\ttell me\n" +
                "\t\tif file_exists(export_folder) of me then\n" +
                "\t\t\ttry\n" +
                "\t\t\t\tdisplay alert \"The file already exists. Do you want to replace it?\" buttons {\"Cancel\", \"Erase\"} cancel button 1\n" +
                "\t\t\ton error errText number errNum\n" +
                "\t\t\t\tif (errNum is equal to -128) then\n" +
                "\t\t\t\t\treturn\n" +
                "\t\t\t\tend if\n" +
                "\t\t\tend try\n" +
                "\t\t\t\n" +
                "\t\t\t-- Delete the folder\n" +
                "\t\t\tdo shell script \"rm -rf \" & quoted form of POSIX path of export_folder\n" +
                "\t\t\t\n" +
                "\t\telse\n" +
                "\t\t\t-- Create the folder\n" +
                "\t\t\tdo shell script \"mkdir -p \" & quoted form of POSIX path of export_folder\n" +
                "\t\tend if\n" +
                "\tend tell\n" +
                "\t\n" +
                "\tset canvasCount to count of canvases of theDocument\n" +
                "\t\n" +
                "\tset canvasNumber to 1\n" +
                "\trepeat with theCanvas in every canvas of theDocument\n" +
                "\t\t-- Activate the canvas\n" +
                "\t\tset canvas of front window to theCanvas\n" +
                "\t\tset canvas_name to name of theCanvas\n" +
                "\t\tset layerCount to count of layers of theCanvas\n" +
                "\t\t\n" +
                "\t\t-- Prepare filename\n" +
                "\t\tset canvasFilename to \"\"\n" +
                "\t\tset canvasFilename to \"\"\n" +
                "\t\tif ADD_CANVAS_NUMBER then\n" +
                "\t\t\tset canvasFilename to canvasNumber & \"- \"\n" +
                "\t\tend if\n" +
                "\t\tset canvasFilename to canvasFilename & canvas_name\n" +
                "\t\t\n" +
                "\t\t-- Hide all layers, except those beginning with \"*\"\n" +
                "\t\t-- also check if there is only one layer to be exported\n" +
                "\t\trepeat with layerNumber from 1 to layerCount\n" +
                "\t\t\tset theLayer to layer layerNumber of theCanvas\n" +
                "\t\t\tset number_of_layer_to_be_exported to 0\n" +
                "\t\t\t\n" +
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
                "\t\t\n" +
                "\t\t-- Export each layer\n" +
                "\t\trepeat with layerNumber from 1 to layerCount\n" +
                "\t\t\tset theLayer to layer layerNumber of theCanvas\n" +
                "\t\t\t\n" +
                "\t\t\tif (theLayer is prints) and (class of theLayer is not shared layer) then\n" +
                "\t\t\t\tset layer_name to name of theLayer as string\n" +
                "\t\t\t\tset filename to canvasFilename & \" - \" & layer_name & \".\" & exportFileExtension\n" +
                "\t\t\t\tset exportFilename to export_folder & filename\n" +
                "\t\t\t\t\n" +
                "\t\t\t\t-- show the layer, export, then hide the layer\n" +
                "\t\t\t\tif character 1 of layer_name is not \"*\" then\n" +
                "\t\t\t\t\tset visible of theLayer to true\n" +
                "\t\t\t\t\tsave theDocument in file exportFilename\n" +
                "\t\t\t\t\tset visible of theLayer to false\n" +
                "\t\t\t\tend if\n" +
                "\t\t\t\t\n" +
                "\t\t\tend if\n" +
                "\t\t\t\n" +
                "\t\tend repeat\n" +
                "\t\t\n" +
                "\t\tset canvasNumber to canvasNumber + 1\n" +
                "\t\t\n" +
                "\tend repeat\n" +
                "end tell";
        return export;
    }

}
