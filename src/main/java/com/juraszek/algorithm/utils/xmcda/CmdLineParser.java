package com.juraszek.algorithm.utils.xmcda;

import org.springframework.stereotype.Service;

@Service(value = "CmdLineParser")
public class CmdLineParser {

    public CmdLineParser.Arguments parseCmdLineArguments(String[] args) throws InvalidCommandLineException {
        if (args.length != 4)
            throw new InvalidCommandLineException("Invalid number of arguments");
        Arguments arguments = new Arguments();
        for (int index = 0; index <= 2; index += 2) {
            String arg = args[index];
            if ("-i".equals(arg) || "--input-directory".equals(arg))
                arguments.inputDirectory = args[index + 1];
            else if ("-o".equals(arg) || "--output-directory".equals(arg))
                arguments.outputDirectory = args[index + 1];
        }
        if (arguments.inputDirectory == null || arguments.outputDirectory == null)
            throw new InvalidCommandLineException("Missing parameters");
        return arguments;
    }

    static class InvalidCommandLineException extends Exception {
        private static final long serialVersionUID = 3991185595688176975L;
        InvalidCommandLineException(String message) {
            super(message);
        }
    }

    public static class Arguments {
        public String inputDirectory;
        public String outputDirectory;
    }

}
