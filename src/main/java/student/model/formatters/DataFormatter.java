package student.model.formatters;


import java.io.PrintStream;
import java.io.OutputStream;
import java.util.Collection;
import javax.annotation.Nonnull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.IOException;
import java.io.PrintWriter;
import student.model.DomainNameModel.DNRecord;

/**
 * A class to format the data in different ways.
 */
public final class DataFormatter {
    /**
     * Private constructor to prevent instantiation.
     */
    private DataFormatter() {
        // empty
    }

    /**
     * Pretty print the data in a human readable format.
     * 
     * @param records the records to print
     * @param out the output stream to write to
     */
    private static void prettyPrint(Collection<DNRecord> records, OutputStream out) {
        PrintStream pout = new PrintStream(out); // so i can use println
        for (DNRecord record : records) {
            prettySingle(record, pout);
            pout.println();
        }
    }

    /**
     * Pretty print a single record.
     * 
     * Let this as an example, so you didn't have to worry about spacing.
     * 
     * @param record the record to print
     * @param out the output stream to write to
     */
    private static void prettySingle(@Nonnull DNRecord record, @Nonnull PrintStream out) {
        out.println(record.hostname());
        out.println("             IP: " + record.ip());
        out.println("       Location: " + record.city() + ", " + record.region() + ", "
                + record.country() + ", " + record.postal());
        out.println("    Coordinates: " + record.latitude() + ", " + record.longitude());

    }

    /**
     * Write the data as XML.
     * 
     * @param records the records to write
     * @param out the output stream to write to
     * @throws IOException if an I/O error occurs during the serialization process
     */
    private static void writeXmlData(Collection<DNRecord> records, OutputStream out) {
        try {
            // Create a new instance of XmlMapper to handle XML serialization
            XmlMapper mapper = new XmlMapper();
            // Enable identation to pretty-print the XML output.
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            // Create a wrapper object that encapsulates the collection of DNRecord objects.
            DomainXmlWrapper wrapper = new DomainXmlWrapper(records);
            // Serialize the wrapper object to XML and write it to the output stream
            mapper.writeValue(out, wrapper);
        } catch (IOException e) {
            // If an I/O error occurs, print the stack trace to standard error.
            e.printStackTrace();
        }
    }


    /**
     * Write the data as JSON.
     * 
     * @param records the records to write
     * @param out the output stream to write to
     */
    private static void writeJsonData(Collection<DNRecord> records, OutputStream out) {
        try {
            // Create a new instance of ObjectMapper to handle JSON serialization.
            ObjectMapper mapper = new ObjectMapper();
            // Enable indentation to pretty-print the JSON output.
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            // Serialize the collection of DNRecord object to JSON and write it to the output stream.
            mapper.writeValue(out, records);
        } catch (IOException e) {
            // If an I/O error occurs, print the stack trace to standard error.
            e.printStackTrace();
        }
    }

    /**
     * Write the data as CSV.
     * 
     * @param records the records to write
     * @param out the output stream to write to
     */
    private static void writeCSVData(Collection<DNRecord> records, OutputStream out) {
        try (PrintWriter writer = new PrintWriter(out)) {
            // Write the CSV header row.
            writer.println("hostname,ip,city,region,country,postal,latitude,longitude");
            // Iterate through each record in the collection.
            for (DNRecord record : records) {
                // Print format, write each record to CSV in order.
                // hostname of the record, IP address of the record, City of the record.
                // Region of the record, Country of the record, Postal code of the record.
                // Latitude coordinate of the record, Longitude coordinate of the record.
                writer.printf("%s,%s,%s,%s,%s,%s,%f,%f%n", record.hostname(), record.ip(), record.city(), record.region(), 
                    record.country(), record.postal(), record.latitude(), record.longitude());
            }
        }
    }

    /**
     * Write the data in the specified format.
     * 
     * @param records the records to write
     * @param format the format to write the records in
     * @param out the output stream to write to
     */
    public static void write(@Nonnull Collection<DNRecord> records, @Nonnull Formats format,
            @Nonnull OutputStream out) {

        switch (format) {
            case XML:
                writeXmlData(records, out);
                break;
            case JSON:
                writeJsonData(records, out);
                break;
            case CSV:
                writeCSVData(records, out);
                break;
            default:
                prettyPrint(records, out);

        }
    }



}
