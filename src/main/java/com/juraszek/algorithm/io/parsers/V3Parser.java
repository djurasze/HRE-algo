package com.juraszek.algorithm.io.parsers;

import com.juraszek.algorithm.utils.xmcda.XMCDAMessageParser;
import org.xmcda.ProgramExecutionResult;
import org.xmcda.XMCDA;

import java.io.File;

public class V3Parser implements Parser {




    public XMCDA loadXMCDA(String inputDirectory,
                            ProgramExecutionResult executionResult)
    {
        XMCDA xmcda = new XMCDA();
        loadXMCDA(xmcda, new File(inputDirectory, "alternatives.xml"), false,
                executionResult, "alternatives");
        loadXMCDA(xmcda, new File(inputDirectory, "alternativesMatrix.xml"), true,
                executionResult, "alternativesMatrix");
        loadXMCDA(xmcda, new File(inputDirectory, "knownRankings.xml"), false,
                executionResult, "alternativesValues");
        loadXMCDA(xmcda, new File(inputDirectory, "parameters.xml"), true,
                executionResult, "programParameters");
        return xmcda;

    }
    private void loadXMCDA(XMCDA xmcda, final File file, boolean mandatory,
                           ProgramExecutionResult executionResults, String... loadTags) {
        final org.xmcda.parsers.xml.xmcda_v3.XMCDAParser parser = new org.xmcda.parsers.xml.xmcda_v3.XMCDAParser();
        final String baseFilename = file.getName();

        if (!file.exists()) {
            if (mandatory) {
                executionResults.addError("Could not find the mandatory file " + baseFilename);
                return;
            } else
                return;
        }

        try {
            parser.readXMCDA(xmcda, file, loadTags);
        } catch (Throwable throwable) {
            final String msg = String.format("Unable to read & parse the file %s, reason: ", baseFilename);
            executionResults.addError(XMCDAMessageParser.getMessage(msg, throwable));
        }
    }
}
