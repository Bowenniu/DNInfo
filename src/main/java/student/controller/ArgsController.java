package student.controller;

import java.io.OutputStream;
import student.model.DomainNameModel;
import student.model.DomainNameModel.DNRecord;
import student.model.formatters.Formats;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * A controller to handle the arguments.
 */
public class ArgsController {
    /** Model of the application. */
    private DomainNameModel model;
    /** The format to output. */
    private Formats format = Formats.PRETTY;
    /** The format of the output stream defaulting to System.out. */
    private OutputStream output = System.out;
    /** The hostname to look up. */
    private String hostname = "all"; // default to all
    /** The data file path. */
    private String dataFilePath = DomainNameModel.DATABASE;
   
    /**
     * @param hostname the hostname that shall be look up to
     */
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }
    
    /**
     * Set the output format.
     * 
     * @param formatStr the format as string
     */
    public void setFormat(String formatStr) {
        // Check if the provided format string is valid.
        Formats format = Formats.containsValues(formatStr);
        // If format is valid, set format, else, tell the user that it is unknown format.
        if (format != null) {
            this.format = format;
        } else {
            System.err.println("Unknown format: " + formatStr);
        }
    }
    /**
     * Set the output path.
     * 
     * @param outputPath the output path
     */
    public void setOutputPath(String outputPath) {
        try {
            // Set output stream to the output path file
            this.output = new FileOutputStream(outputPath);
        } catch (IOException e) {
            // If the setting path fails, print the message to tell the user the output path can not be set.
            System.err.println("Failed to set output path: " + outputPath);
            e.printStackTrace();
        }
    }

    /**
     * Set the data file path.
     * 
     * @param dataFilePath the data file path
     */
    public void setDataFilePath(String dataFilePath) {
        // Set data file path.
        this.dataFilePath = dataFilePath;
        // Get instance of the model by using the directed data file path.
        this.model = DomainNameModel.getInstance(dataFilePath);
    }

    /**
     * Process the hostname.
     * 
     * @param hostname the hostname to process
     */
    public void processHostname(String hostname) {
        // Set host name.
        this.hostname = hostname;
        // Get the instance of the model by using the directed data file path
        model = DomainNameModel.getInstance(dataFilePath);

        if ("all".equalsIgnoreCase(hostname)) {
            // If host name is all, collect all records and save at output
            List<DNRecord> records = model.getRecords();
            DomainNameModel.writeRecords(records, format, output);
        } else {
            // If host name is not all, get record for directed hostname and save at output
            DNRecord record = model.getRecord(hostname);
            DomainNameModel.writeRecords(List.of(record), format, output);
        }

        try {
            // try to close the output stream.
            output.close();
        } catch (IOException e) {
            // If failed to close the output stream, print message and tell the user about the error.ß
            System.err.println("Unable to close the output stream.");
            e.printStackTrace();
        }
    }

    /**
     * Get the help message. Left this here, so you didn't have to write it - however you are free
     * to change it and the file name if you want/need to.
     * 
     * @return the help message
     */
    public String getHelp() {
        return """
                DNInfoApp [hostname|all] [-f json|xml|csv|pretty] [-o file path] [-h | --help] [--data filepath]

                Looks up the information for a given hostname (url) or displays information for
                all domains in the database. Can be output in json, xml, csv, or pretty format.
                If -o file is provided, the output will be written to the file instead of stdout.

                --data is mainly used in testing to provide a different data file, defaults to the hostrecords.xml file.
                """;
    }



}
