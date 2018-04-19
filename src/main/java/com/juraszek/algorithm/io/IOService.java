package com.juraszek.algorithm.io;

import com.juraszek.algorithm.models.HREModel;
import com.juraszek.algorithm.utils.xmcda.CmdLineParser;
import com.juraszek.algorithm.utils.xmcda.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xmcda.ProgramExecutionResult;
import org.xmcda.XMCDA;
import org.xmcda.converters.v2_v3.XMCDAConverter;
import org.xmcda.parsers.xml.xmcda_v2.XMCDAParser;

import java.io.File;
import java.util.Map;

import static com.juraszek.algorithm.utils.xmcda.XMCDAMessageParser.getMessage;


@Service(value = "HreIOService")
public class IOService {

    @Autowired
    private DataLoader dataLoader;
    @Autowired
    private ProgramExecutionResultsService programExecutionResultsService;
    @Autowired
    private InputsHandler inputsHandler;
    @Autowired
    private OutputsHandler outputsHandler;

    public HREModel readInputs(CmdLineParser.Arguments params, ProgramExecutionResult executionResult,
                               File prgExecResults) throws Exception {
        XMCDA xmcda;

        final String inputDirectory = params.inputDirectory;

        xmcda = dataLoader.loadXMCDA(inputDirectory, executionResult);

       if (!(executionResult.isOk() || executionResult.isWarning())) {
            programExecutionResultsService.writeProgramExecutionResultsAndExit(prgExecResults, executionResult, "Invalid inputs.", params.xmcdaVersion);
        }

        final HREModel inputs = inputsHandler.checkAndExtractInputs(xmcda, executionResult);

        if (!(executionResult.isOk() || executionResult.isWarning()) || inputs == null) {
            programExecutionResultsService.writeProgramExecutionResultsAndExit(prgExecResults, executionResult, "Invalid inputs format.", params.xmcdaVersion);
        }

        return inputs;

    }


    public void writeOutput(CmdLineParser.Arguments params, HREModel model,
                            ProgramExecutionResult executionResult, File prgExecResults) throws Throwable {
        if(params.xmcdaVersion == Version.v2) {
            writeOutputV2(params, model, executionResult, prgExecResults);
        } else if (params.xmcdaVersion == Version.v3) {
            writeOutputV3(params, model, executionResult, prgExecResults);
        }
    }

    private void writeOutputV3(CmdLineParser.Arguments params, HREModel model,
                            ProgramExecutionResult executionResult, File prgExecResults) throws Throwable {
        Map<String, XMCDA> results = outputsHandler.convert(model);

        final org.xmcda.parsers.xml.xmcda_v3.XMCDAParser parser = new org.xmcda.parsers.xml.xmcda_v3.XMCDAParser();

        for (String key : results.keySet()) {
            File outputFile = new File(params.outputDirectory, String.format("%s.xml", key));
            try {
                parser.writeXMCDA(results.get(key), outputFile, outputsHandler.xmcdaV3Tag(key));
            } catch (Throwable throwable) {
                final String err = String.format("Error while writing %s.xml, reason: ", key);
                executionResult.addError(getMessage(err, throwable));
                //noinspection ResultOfMethodCallIgnored
                outputFile.delete();
            }
        }

        programExecutionResultsService.writeProgramExecutionResults(prgExecResults, executionResult, params.xmcdaVersion);
    }


    private void writeOutputV2(CmdLineParser.Arguments params, HREModel model,
                            ProgramExecutionResult executionResult, File prgExecResults) throws Throwable {
        Map<String, XMCDA> results = outputsHandler.convert(model);

        /* XMCDA v2 programs prefer not to embed value in a <values> tag */
        XMCDAConverter.omit_values_in_alternativesValues = true;

        for (String key : results.keySet()) {
            org.xmcda.v2.XMCDA results_v2;
            File outputFile = new File(params.outputDirectory, String.format("%s.xml", key));

            try
            {
                results_v2 = XMCDAConverter.convertTo_v2(results.get(key));
                if ( results_v2 == null )
                    throw new IllegalStateException("Conversion from v3 to v2 returned a null value");
            }
            catch (Throwable t)
            {
                executionResult.addError(getMessage("Could not convert alternativesValues into XMCDA_v2, reason: ", t));
                programExecutionResultsService.writeProgramExecutionResults(prgExecResults, executionResult, params.xmcdaVersion);
                return;
            }

            try
            {
                XMCDAParser.writeXMCDA(results_v2, outputFile);
            }
            catch (Throwable t)
            {
                executionResult.addError(getMessage("Error while writing alternativesValues.xml, reason: ", t));
                /* Whatever the error is, clean up the file: we do not want to leave an empty or partially-written file */
                outputFile.delete();
                programExecutionResultsService.writeProgramExecutionResults(prgExecResults, executionResult, params.xmcdaVersion);
                return;
            }
        }
        programExecutionResultsService.writeProgramExecutionResults(prgExecResults, executionResult, params.xmcdaVersion);
    }
}
