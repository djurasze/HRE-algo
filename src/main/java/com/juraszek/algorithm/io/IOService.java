package com.juraszek.algorithm.io;

import com.juraszek.algorithm.models.HREModel;
import com.juraszek.algorithm.utils.xmcda.CmdLineParser;
import com.juraszek.algorithm.utils.xmcda.XMCDAMessageParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xmcda.ProgramExecutionResult;
import org.xmcda.XMCDA;

import java.io.File;
import java.util.Map;


@Service(value = "HreIOService")
public class IOService {

    @Autowired
    ProgramExecutionResultsService programExecutionResultsService;
    @Autowired
    InputsHandler inputsHandler;
    @Autowired
    OutputsHandler outputsHandler;

    public HREModel readInputs(CmdLineParser.Arguments params, ProgramExecutionResult executionResult,
                               File prgExecResults) throws Exception {
        final XMCDA xmcda = new XMCDA();

        final String inputDirectory = params.inputDirectory;

        loadXMCDAv3(xmcda, new File(inputDirectory, "alternatives.xml"), true,
                executionResult, "alternatives");
        loadXMCDAv3(xmcda, new File(inputDirectory, "alternativesMatrix.xml"), true,
                executionResult, "alternativesMatrix");
        loadXMCDAv3(xmcda, new File(inputDirectory, "alternativesValues.xml"), false,
                executionResult, "alternativesValues");
        loadXMCDAv3(xmcda, new File(inputDirectory, "parameters.xml"), true,
                executionResult, "programParameters");

        if (!(executionResult.isOk() || executionResult.isWarning())) {
            programExecutionResultsService.writeProgramExecutionResultsAndExit(prgExecResults, executionResult, "Missing input files.");
        }

        final HREModel inputs = inputsHandler.checkAndExtractInputs(xmcda, executionResult);

        if (!(executionResult.isOk() || executionResult.isWarning()) || inputs == null) {
            programExecutionResultsService.writeProgramExecutionResultsAndExit(prgExecResults, executionResult, "Invalid inputs format.");
        }

        return inputs;

    }

    private void loadXMCDAv3(XMCDA xmcda, final File file, boolean mandatory,
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


    public void writeOutput(CmdLineParser.Arguments params, HREModel model,
                            ProgramExecutionResult executionResult, File prgExecResults) throws Throwable {
        Map<String, XMCDA> results = outputsHandler.convert(model);

        final org.xmcda.parsers.xml.xmcda_v3.XMCDAParser parser = new org.xmcda.parsers.xml.xmcda_v3.XMCDAParser();

        for (String key : results.keySet()) {
            File outputFile = new File(params.outputDirectory, String.format("%s.xml", key));
            try {
                parser.writeXMCDA(results.get(key), outputFile, outputsHandler.xmcdaV3Tag(key));
            } catch (Throwable throwable) {
                final String err = String.format("Error while writing %s.xml, reason: ", key);
                executionResult.addError(XMCDAMessageParser.getMessage(err, throwable));
                //noinspection ResultOfMethodCallIgnored
                outputFile.delete();
            }
        }

        programExecutionResultsService.writeProgramExecutionResults(prgExecResults, executionResult);
    }
}
