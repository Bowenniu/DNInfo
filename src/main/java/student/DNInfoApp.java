package student;

import student.controller.ArgsController;

/**
 * Main driver for the program.
 * 
 * DO NOT modify the name of this class (we call main directly in our test code).
 * 
 */
public final class DNInfoApp {

    /** Private constructor to prevent instantiation. */
    private DNInfoApp() {
        // empty
    }

    /**
     * Main entry point for the program.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) { // Main function
        ArgsController controller = new ArgsController(); // create a new ArgsController in order to deal with the user input.
        // If the length of the command line is greater than 0 (which means there are arguments present).
        if(args.length > 0) {
            // Here, assign the first argument to the hostname varaible.
            String hostname = args[0];
            // Iterate through the remaining arguments
            for(int i = 1; i < args.length; i++) {
                // Check the argument and set the corresponding property in the controller.
                switch (args[i]) {
                    case "-f":
                        // Check if there is a format argument after -f
                        if(i + 1 < args.length) {
                            String format = args[i + 1];
                            controller.setFormat(format);
                            // Move to the next argument.
                            i++;
                        }
                        break;
                    case "-o":
                        // Check if there is an output path argument after -o.
                        if(i + 1 < args.length) {
                            String outputPath = args[i + 1];
                            controller.setOutputPath(outputPath);
                            // Move to the next argument.
                            i++;
                        }
                        break;
                    case "--data":
                        // Check if there is a data file path argument after --data.
                        if (i + 1 < args.length) {
                            String dataFilePath = args[i + 1];
                            controller.setDataFilePath(dataFilePath);
                            i++; // Move to the next argument
                        }
                        break;
                    case"-h":
                    case"--help":
                        // Display the help message and exit.
                        System.out.println(controller.getHelp());
                        return;
                    default:
                        // Display the error message for unknown argument and display help
                        System.err.println("Unknown argument: " + args[i]);
                        System.err.println(controller.getHelp());
                        // Exit the program if an unknown argument is found.
                        return;
                    
                }
            }
            controller.processHostname(hostname);
        } else {
            System.err.println("Usage: DNInfoapp [hostname|all] [-f json|xml|csv|pretty] [-o file path] [--data filepath] [-h | --help]");
        }
    }
}
